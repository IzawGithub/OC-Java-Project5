package com.safetynet.api.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynet.api.helper.IStackLog;
import com.safetynet.api.helper.LoadData;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.dto.InitData;

@Repository
public class MedicalRecordRepository implements CRUDRepository<MedicalRecord>, IStackLog {
    // Public

    // -- Public ctors --

    @Autowired
    public MedicalRecordRepository(LoadData jsonData) {
        this(jsonData.loadDataJson());
    }

    public MedicalRecordRepository(String data) {
        var logger = LoggerFactory.getLogger(getClass().getName());
        logger.trace("Initialising '{}' data", getCurrentMethod());
        try {
            var arrayMedicalRecords = new ObjectMapper().readValue(data, InitData.class).getMedicalrecords();
            medicalRecordMap = initData(arrayMedicalRecords);
            logger.trace("Initialised medicalRecordMap");
        } catch (JsonProcessingException e) {
            logger.error("'{}' error: '{}'", getCurrentMethod(), e);
        }
    }

    // Private

    // -- Overrides --

    @Override
    public Map<Integer, MedicalRecord> getMap() {
        return medicalRecordMap;
    }

    // -- Vars --

    Map<Integer, MedicalRecord> medicalRecordMap = new HashMap<>();

}
