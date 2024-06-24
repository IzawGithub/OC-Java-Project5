package com.safetynet.api.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.CRUDRepository;
import com.safetynet.api.repository.MedicalRecordRepository;

@Service
@RequiredArgsConstructor
public class MedicalRecordService implements CRUDService<MedicalRecord> {
    // Public

    // -- Read --

    public MedicalRecord getMedicalRecord(String firstname, String lastName) {
        var medicalRecord = MedicalRecord.builder()
                .firstName(firstname)
                .lastName(lastName)
                .build();
        return get(medicalRecord);
    }

    // -- Update --

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updateMedicalRecord)
            throws CRUDException {
        var medicalRecord = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        updateMedicalRecord.setFirstName(firstName);
        updateMedicalRecord.setLastName(lastName);

        return update(medicalRecord, updateMedicalRecord);
    }

    // -- Delete --

    public MedicalRecord deleteMedicalRecord(String firstName, String lastName) throws CRUDException {
        var medicalRecord = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return delete(medicalRecord);
    }

    // Private

    // -- Overrides --

    @Override
    public CRUDRepository<MedicalRecord> getRepository() {
        return medicalRecordRepository;
    }

    // -- Interfaces --

    private final MedicalRecordRepository medicalRecordRepository;
}
