package com.safetynet.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.model.dto.DataChildAlert;
import com.safetynet.api.model.dto.DataFire;
import com.safetynet.api.model.dto.DataFlood;
import com.safetynet.api.model.dto.DataInfo;
import com.safetynet.api.model.dto.DataServedByFireStation;
import com.safetynet.api.model.dto.DataFire.DataFirePersonWithRecord;
import com.safetynet.api.model.dto.DataFlood.DataFloodPersonWithRecord;

@Service
@RequiredArgsConstructor
public class URLsService {
    // Public

    public List<DataChildAlert> getChildAlert(String address) {
        List<Person> listPersonSameAddress = personService.getAll()
                .stream()
                // Remove if either not the same address
                // or null
                .filter(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(
                            person.getFirstName(),
                            person.getLastName());
                    return person.getAddress().equals(address) &&
                            Objects.nonNull(medicalRecord);

                })
                .collect(Collectors.toList());

        return listPersonSameAddress.stream()
                // Remove if not a child
                .filter(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(
                            person.getFirstName(),
                            person.getLastName());
                    return isChild(medicalRecord);
                })
                .map(child -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(
                            child.getFirstName(),
                            child.getLastName());

                    List<Person> family = listPersonSameAddress.stream()
                            .filter(person -> !person.equals(child))
                            .collect(Collectors.toList());
                    return DataChildAlert.builder()
                            .age(age(medicalRecord))
                            .child(child)
                            .family(family)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Set<String> getCommunityEmail(String city) {
        return personService.getAll()
                .stream()
                .filter(person -> person.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet());
    }

    public List<DataFire> getFire(String address) {
        return personService.getAll()
                .stream()
                .filter(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(
                            person.getFirstName(),
                            person.getLastName());

                    return person.getAddress().equals(address) &&
                            Objects.nonNull(medicalRecord);
                })
                .map(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(
                            person.getFirstName(),
                            person.getLastName());
                    var fireStation = fireStationService.getAll()
                            .stream()
                            .filter(station -> station.getAddress().contains(address))
                            .findFirst()
                            .get(); // Should not throw as would have been filtered above

                    return DataFire.builder()
                            .age(age(medicalRecord))
                            .fireStation(fireStation)
                            .persons(DataFirePersonWithRecord.builder()
                                    .medicalRecord(medicalRecord)
                                    .person(person)
                                    .build())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public DataServedByFireStation getServedByFireStation(String station) {
        var fireStation = fireStationService.getFireStation(station);

        // Wrapper is necessary, to fix the compile error:
        // Local variable numberChilds defined in an enclosing scope must be final or
        // effectively final
        var numberAdults = new Object() {
            Integer number = 0;
        };
        var numberChilds = new Object() {
            Integer number = 0;
        };

        List<Person> resultPerson = personService.getAll()
                .stream()
                .filter(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(person.getFirstName(),
                            person.getLastName());
                    if (Objects.isNull(fireStation) || Objects.isNull(medicalRecord)) {
                        return false;
                    }
                    return fireStation.getAddress().contains(person.getAddress());
                })
                .peek(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(person.getFirstName(),
                            person.getLastName());
                    if (isChild(medicalRecord)) {
                        numberChilds.number++;
                    } else {
                        numberAdults.number++;
                    }
                })
                .collect(Collectors.toList());
        return DataServedByFireStation.builder()
                .persons(resultPerson)
                .numberAdults(numberAdults.number)
                .numberChilds(numberChilds.number)
                .build();
    }

    public List<DataFlood> getFlood(List<String> stations) {
        var listStation = stations.stream()
                .map(fireStationService::getFireStation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return listStation
                .stream()
                .map(station -> {
                    var mapPersonsWithRecords = personService.getAll()
                            .stream()
                            .filter(person -> station.getAddress()
                                    .contains(person.getAddress()))
                            .map(person -> {
                                var medicalRecord = medicalRecordService
                                        .getMedicalRecord(
                                                person.getFirstName(),
                                                person.getLastName());

                                return DataFloodPersonWithRecord.builder()
                                        .age(age(medicalRecord))
                                        .medicalRecord(medicalRecord)
                                        .person(person)
                                        .build();
                            })
                            .collect(Collectors.groupingBy(
                                    person -> person.getPerson().getAddress()));

                    return DataFlood.builder()
                            .fireStation(station)
                            .persons(mapPersonsWithRecords)
                            .build();
                })
                .collect(Collectors.toList());

    }

    public List<DataInfo> getPersonInfo(String firstName, String lastName) {
        return personService.getAll()
                .stream()
                .filter(person -> {
                    // No specific person, deactivate filter
                    if (Objects.isNull(firstName)
                            || Objects.isNull(lastName)) {
                        return true;
                    }

                    var filterPerson = Person.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .build();
                    return person.equals(filterPerson);
                })
                .map(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecord(
                            person.getFirstName(),
                            person.getLastName());

                    return DataInfo.builder()
                            .age(age(medicalRecord))
                            .person(person)
                            .medicalRecord(medicalRecord)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Set<String> getPhoneAlert(String station) {
        var fireStation = fireStationService.getFireStation(station);

        return personService.getAll()
                .stream()
                .filter(person -> {
                    if (Objects.isNull(fireStation)) {
                        return false;
                    }
                    return fireStation.getAddress().contains(person.getAddress());
                })
                .map(Person::getPhone)
                .collect(Collectors.toSet());
    }

    // Private

    // -- Interfaces --

    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;
    private final PersonService personService;

    // -- Functions --

    private boolean isChild(MedicalRecord medicalRecord) {
        return age(medicalRecord) < 18;
    }

    private Integer age(MedicalRecord medicalRecord) {
        var birthdate = LocalDate.parse(medicalRecord.getBirthdate(),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        var currentDate = LocalDate.now();
        return currentDate.getYear() - birthdate.getYear();
    }
}
