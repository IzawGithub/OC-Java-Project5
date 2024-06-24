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
import com.safetynet.api.model.Person;
import com.safetynet.api.model.dto.InitData;

@Repository
public class PersonRepository implements CRUDRepository<Person>, IStackLog {
    // Public

    // -- Public ctors --

    @Autowired
    public PersonRepository(LoadData jsonData) {
        this(jsonData.loadDataJson());
    }

    public PersonRepository(String data) {
        var logger = LoggerFactory.getLogger(getClass().getName());
        logger.trace("Initialising '{}' data", getCurrentMethod());
        try {
            var arrayPersons = new ObjectMapper().readValue(data, InitData.class).getPersons();
            personMap = initData(arrayPersons);
            logger.trace("Initialised personMap");
        } catch (JsonProcessingException e) {
            logger.error("'{}' error: '{}'", getCurrentMethod(), e);
        }
    }

    // Private

    // -- Overrides --

    @Override
    public Map<Integer, Person> getMap() {
        return personMap;
    }

    // -- Vars --

    Map<Integer, Person> personMap = new HashMap<>();

}
