package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.model.dto.DataChildAlert;
import com.safetynet.api.model.dto.DataFire;
import com.safetynet.api.model.dto.DataFlood;
import com.safetynet.api.model.dto.DataInfo;
import com.safetynet.api.model.dto.DataServedByFireStation;
import com.safetynet.api.model.dto.DataFire.DataFirePersonWithRecord;
import com.safetynet.api.model.dto.DataFlood.DataFloodPersonWithRecord;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.FireStationService;
import com.safetynet.api.service.MedicalRecordService;
import com.safetynet.api.service.PersonService;
import com.safetynet.api.service.URLsService;

class URLsControllerTest {
    final Person testAdultPerson = Person.builder()
            .firstName("Adult")
            .lastName("Doe")
            .address("1 JohnDoeStreets")
            .city("JohnDoeCity")
            .zip("1337")
            .phone("012-34-5678")
            .email("adult.doe@j ohndoe.com")
            .build();
    final Person testChildPerson = Person.builder()
            .firstName("Child")
            .lastName("Doe")
            .address("1 JohnDoeStreets")
            .city("JohnDoeCity")
            .zip("1337")
            .phone("901-23-4567")
            .email("child.doe@johndoe.com")
            .build();
    final MedicalRecord testAdultMedical = MedicalRecord.builder()
            .firstName("Adult")
            .lastName("Doe")
            .birthdate("01/01/1970")
            .medications(new String[] { "medicine:1337mg" })
            .allergies(new String[] { "allergy" })
            .build();
    final MedicalRecord testChildMedical = MedicalRecord.builder()
            .firstName("Child")
            .lastName("Doe")
            .birthdate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))
            .medications(new String[] { "medicine:1337mg" })
            .allergies(new String[] { "allergy" })
            .build();
    final FireStation testFireStation = FireStation.builder()
            .address(Set.of("TestAddress", "1 JohnDoeStreets"))
            .station("TestStation")
            .build();

    FireStationRepository fireStationRepository;
    MedicalRecordRepository medicalRecordRepository;
    PersonRepository personRepository;

    FireStationService fireStationService;
    MedicalRecordService medicalRecordService;
    PersonService personService;

    URLsService testService;
    URLsController testController;

    @BeforeEach
    void setUpPerTest() {
        fireStationRepository = new FireStationRepository("{}");
        medicalRecordRepository = new MedicalRecordRepository("{}");
        personRepository = new PersonRepository("{}");

        fireStationService = new FireStationService(fireStationRepository);
        medicalRecordService = new MedicalRecordService(medicalRecordRepository);
        personService = new PersonService(personRepository);

        testService = new URLsService(fireStationService, medicalRecordService, personService);
        testController = new URLsController(testService);
    }

    @Test
    void childAlert() throws CRUDException {
        var expected = List.of(
                DataChildAlert.builder()
                        .age(0)
                        .child(testChildPerson)
                        .family(List.of(testAdultPerson))
                        .build());

        personService.create(testAdultPerson);
        medicalRecordService.create(testAdultMedical);
        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getChildAlert(testChildPerson.getAddress()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void communityEmail() throws CRUDException {
        var expected = Set.of(testAdultPerson.getEmail(), testChildPerson.getEmail());

        personService.create(testAdultPerson);
        personService.create(testChildPerson);

        var actual = testController.getCommunityEmail(testAdultPerson.getCity()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void fire() throws CRUDException {
        var expected = List.of(
                DataFire.builder()
                        .age(0)
                        .fireStation(testFireStation)
                        .persons(DataFirePersonWithRecord.builder()
                                .medicalRecord(testChildMedical)
                                .person(testChildPerson)
                                .build())
                        .build());

        fireStationService.create(testFireStation);
        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getFire(testChildPerson.getAddress()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void fireStation() throws CRUDException {
        var expected = DataServedByFireStation.builder()
                .numberAdults(1)
                .numberChilds(1)
                .persons(List.of(testChildPerson, testAdultPerson))
                .build();

        fireStationService.create(testFireStation);
        personService.create(testAdultPerson);
        medicalRecordService.create(testAdultMedical);
        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getServedByFireStation(testFireStation.getStation()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void flood() throws CRUDException {
        var expected = List.of(
                DataFlood.builder()
                        .fireStation(testFireStation)
                        .persons(Map.of(
                                testChildPerson.getAddress(),
                                List.of(
                                        DataFloodPersonWithRecord.builder()
                                                .age(0)
                                                .medicalRecord(testChildMedical)
                                                .person(testChildPerson)
                                                .build())))
                        .build());

        fireStationService.create(testFireStation);
        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getFlood(List.of(testFireStation.getStation())).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void personInfoNoFilter() throws CRUDException {
        var expected = List.of(
                DataInfo.builder()
                        .age(0)
                        .person(testChildPerson)
                        .medicalRecord(testChildMedical)
                        .build());

        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getPersonInfo(null, null).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void personInfoNoFilterOnLastName() throws CRUDException {
        var expected = List.of(
                DataInfo.builder()
                        .age(0)
                        .person(testChildPerson)
                        .medicalRecord(testChildMedical)
                        .build());

        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getPersonInfo(testChildPerson.getFirstName(), null).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void personInfoWithFilter() throws CRUDException {
        var expected = List.of(
                DataInfo.builder()
                        .age(0)
                        .person(testChildPerson)
                        .medicalRecord(testChildMedical)
                        .build());

        personService.create(testChildPerson);
        medicalRecordService.create(testChildMedical);

        var actual = testController.getPersonInfo(testChildPerson.getFirstName(), testChildPerson.getLastName())
                .getBody();
        assertEquals(expected, actual);
    }

    @Test
    void phoneAlert() throws CRUDException {
        var expected = Set.of("012-34-5678", "901-23-4567");

        fireStationService.create(testFireStation);
        personService.create(testAdultPerson);
        personService.create(testChildPerson);

        var actual = testController.getPhoneAlert(testFireStation.getStation()).getBody();
        assertEquals(expected, actual);
    }
}
