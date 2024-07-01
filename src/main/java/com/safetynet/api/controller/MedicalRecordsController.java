package com.safetynet.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.helper.IStackLog;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordsController implements IStackLog {
    // Public

    // -- Create --

    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord)
            throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(medicalRecordService.create(medicalRecord), HttpStatus.OK);
    }

    // -- Read --

    @GetMapping("/{firstName}-{lastName}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(medicalRecordService.getMedicalRecord(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(medicalRecordService.getAll(), HttpStatus.OK);
    }

    // -- Update --

    @PutMapping("/{firstName}-{lastName}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updateMedicalRecord) throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(medicalRecordService.updateMedicalRecord(firstName, lastName, updateMedicalRecord),
                HttpStatus.OK);
    }

    // -- Delete --

    @DeleteMapping("/{firstName}-{lastName}")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(medicalRecordService.deleteMedicalRecord(firstName, lastName), HttpStatus.OK);
    }

    // Private

    // -- Interface --

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final MedicalRecordService medicalRecordService;

}
