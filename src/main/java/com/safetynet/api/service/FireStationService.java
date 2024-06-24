package com.safetynet.api.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.dto.DataUpdateFireStation;
import com.safetynet.api.repository.CRUDRepository;
import com.safetynet.api.repository.FireStationRepository;

@Service
@RequiredArgsConstructor
public class FireStationService implements CRUDService<FireStation> {
    // Public

    // -- Read --

    public FireStation getFireStation(String station) {
        var fireStation = FireStation.builder()
                .station(station)
                .build();

        return get(fireStation);
    }

    // -- Update --

    public FireStation updateFireStation(DataUpdateFireStation payload) throws CRUDException {
        return update(payload.getOld(), payload.getUpdated());
    }

    // -- Delete --

    public FireStation deleteFireStation(String station) throws CRUDException {
        var fireStation = FireStation.builder()
                .station(station)
                .build();

        return delete(fireStation);
    }

    // Private

    // -- Overrides --

    @Override
    public CRUDRepository<FireStation> getRepository() {
        return fireStationRepository;
    }

    // -- Interfaces --

    private final FireStationRepository fireStationRepository;
}
