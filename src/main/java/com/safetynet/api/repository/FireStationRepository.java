package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynet.api.helper.IStackLog;
import com.safetynet.api.helper.LoadData;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.dto.InitData;

@Repository
public class FireStationRepository implements CRUDRepository<FireStation>, IStackLog {
    // Public

    // -- Function --

    @Autowired
    public FireStationRepository(LoadData jsonData) {
        this(jsonData.loadDataJson());
    }

    public FireStationRepository(String data) {
        var logger = LoggerFactory.getLogger(getClass().getName());
        logger.trace("Initialising '{}' data", getCurrentMethod());
        try {
            var arrayDataFireStations = new ObjectMapper().readValue(data, InitData.class).getFirestations();
            if (Objects.isNull(arrayDataFireStations)) {
                return;
            }

            Map<String, Set<String>> transientData = new HashMap<>();
            for (var dirtyData : arrayDataFireStations) {
                transientData.computeIfAbsent(dirtyData.getStation(), monad -> new HashSet<>())
                        .add(dirtyData.getAddress());
            }
            List<FireStation> arrayFireStations = new ArrayList<>();
            transientData.forEach((station, address) -> arrayFireStations
                    .add(FireStation.builder().address(address).station(station).build()));

            fireStationMap = initData(arrayFireStations);
            logger.trace("Initialised fireStationMap");
        } catch (IOException e) {
            logger.error("'{}' error: '{}'", getCurrentMethod(), e);
        }
    }

    // Private

    @Override
    public Map<Integer, FireStation> getMap() {
        return fireStationMap;
    }

    // -- Var --

    Map<Integer, FireStation> fireStationMap = new HashMap<>();

}
