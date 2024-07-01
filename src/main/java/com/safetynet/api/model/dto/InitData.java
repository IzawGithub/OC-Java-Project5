package com.safetynet.api.model.dto;

import java.util.List;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

import lombok.Data;

@Data
public class InitData {
    List<Person> persons;
    List<DataFireStation> firestations;
    List<MedicalRecord> medicalrecords;
}
