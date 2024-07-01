package com.safetynet.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.exception.CRUDException.ECRUDException;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.service.MedicalRecordService;

class MedicalRecordControllerTest {
    final MedicalRecord testMedicalRecord = MedicalRecord.builder()
            .firstName("John")
            .lastName("Doe")
            .birthdate("01/01/1970")
            .medications(new String[] { "medicine:1337mg" })
            .allergies(new String[] { "allergy" })
            .build();

    MedicalRecordRepository testRepository;
    MedicalRecordService testService;
    MedicalRecordsController testController;

    @BeforeEach
    void setUpPerTest() {
        testRepository = new MedicalRecordRepository("{}");
        testService = new MedicalRecordService(testRepository);
        testController = new MedicalRecordsController(testService);
    }

    // Create

    @Test
    void createMedicalRecord() throws CRUDException {
        var expected = testMedicalRecord;

        var actual = testController.createMedicalRecord(expected).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void createMedicalRecordAlreadyExist() throws CRUDException {
        testController.createMedicalRecord(testMedicalRecord);
        var expected = new CRUDException(ECRUDException.ALREADY_EXIST, testMedicalRecord);

        var actual = assertThrows(CRUDException.class, () -> testController.createMedicalRecord(testMedicalRecord));
        assertEquals(expected, actual);
    }

    // Read

    @Test
    void getMedicalRecord() throws CRUDException {
        var expected = testMedicalRecord;
        testController.createMedicalRecord(expected);

        var actual = testController.getMedicalRecord(expected.getFirstName(), expected.getLastName()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void getMedicalRecordDoesNotExist() {
        MedicalRecord expected = null;

        var actual = testController.getMedicalRecord(testMedicalRecord.getFirstName(), testMedicalRecord.getLastName())
                .getBody();
        assertEquals(expected, actual);
    }

    @Test
    void getAllMedicalRecords() throws CRUDException {
        List<MedicalRecord> expected = List.of(testMedicalRecord);
        testController.createMedicalRecord(testMedicalRecord);

        var actual = testController.getAllMedicalRecords().getBody();
        assertEquals(expected, actual);
    }

    // Update

    @Test
    void updateMedicalRecord() throws CRUDException {
        var expected = MedicalRecord.builder()
                .firstName("Jahn")
                .lastName("Doe")
                .build();
        testController.createMedicalRecord(testMedicalRecord);

        var actual = testController
                .updateMedicalRecord(testMedicalRecord.getFirstName(), testMedicalRecord.getLastName(), expected)
                .getBody();
        assertEquals(expected, actual);
        assertNotEquals(testMedicalRecord, actual);
    }

    @Test
    void updateMedicalRecordDoesntExist() {
        MedicalRecord updateMedicalRecord = MedicalRecord.builder()
                .firstName("Jahn")
                .lastName("Doe")
                .build();

        var expected = new CRUDException(ECRUDException.DOES_NOT_EXIST, testMedicalRecord);
        var actual = assertThrows(CRUDException.class,
                () -> testController.updateMedicalRecord(testMedicalRecord.getFirstName(),
                        testMedicalRecord.getLastName(),
                        updateMedicalRecord));

        assertEquals(expected, actual);
    }

    // Delete

    @Test
    void deleteMedicalRecord() throws CRUDException {
        var expected = testMedicalRecord;
        testController.createMedicalRecord(expected);

        var actual = testController.deleteMedicalRecord(expected.getFirstName(), expected.getLastName()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void deleteMedicalRecordThatDoesntExist() {
        var expected = new CRUDException(ECRUDException.DOES_NOT_EXIST, testMedicalRecord);
        var actual = assertThrows(CRUDException.class, () -> testController
                .deleteMedicalRecord(testMedicalRecord.getFirstName(), testMedicalRecord.getLastName()));

        assertEquals(expected, actual);
    }
}
