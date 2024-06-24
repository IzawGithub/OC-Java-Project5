package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.exception.CRUDException.ECRUDException;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.dto.DataUpdateFireStation;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.service.FireStationService;

class FireStationControllerTest {
    final FireStation testFireStation = FireStation.builder()
            .address(Set.of("TestAddress"))
            .station("TestStation")
            .build();

    FireStationRepository testRepository;
    FireStationService testService;
    FireStationController testController;

    @BeforeEach
    void setUpPerTest() {
        testRepository = new FireStationRepository("{}");
        testService = new FireStationService(testRepository);
        testController = new FireStationController(testService);
    }

    // Create

    @Test
    void createFireStation() throws CRUDException {
        var expected = testFireStation;

        var actual = testController.createFireStation(expected).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void createFireStationAlreadyExist() throws CRUDException {
        testController.createFireStation(testFireStation);
        var expected = new CRUDException(ECRUDException.ALREADY_EXIST, testFireStation);

        var actual = assertThrows(CRUDException.class, () -> {
            testController.createFireStation(testFireStation);
        });
        assertEquals(expected, actual);
    }

    // Read

    @Test
    void getFireStation() throws CRUDException {
        var expected = testFireStation;
        testController.createFireStation(expected);

        var actual = testController.getFireStation(expected.getStation()).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void getFireStationDoesNotExist() {
        FireStation expected = null;

        var actual = testController.getFireStation(testFireStation.getStation()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void getAllFireStation() throws CRUDException {
        List<FireStation> expected = List.of(testFireStation);
        testController.createFireStation(testFireStation);

        var actual = testController.getAllFireStations().getBody();
        assertEquals(expected, actual);
    }

    // Update

    @Test
    void updateFireStation() throws CRUDException {
        var expected = FireStation.builder()
                .address(Set.of("UpdatedAddress"))
                .station("UpdatedStation")
                .build();
        testController.createFireStation(testFireStation);

        var payload = DataUpdateFireStation.builder()
                .old(testFireStation)
                .updated(expected)
                .build();
        var actual = testController.updateFireStation(payload).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void updateFireStationDoesntExist() {
        var updateFireStation = FireStation.builder()
                .address(Set.of("UpdatedAddress"))
                .station("UpdatedStation")
                .build();

        var expected = new CRUDException(ECRUDException.DOES_NOT_EXIST, testFireStation);
        var actual = assertThrows(CRUDException.class, () -> {
            var payload = DataUpdateFireStation.builder()
                    .old(testFireStation)
                    .updated(updateFireStation)
                    .build();
            testController.updateFireStation(payload);
        });

        assertEquals(expected, actual);
    }

    // Delete

    @Test
    void deleteFireStation() throws CRUDException {
        var expected = FireStation.builder()
                .station(testFireStation.getStation())
                .build();
        testController.createFireStation(expected);

        var actual = testController.deleteFireStation(expected.getStation()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void deleteFireStationThatDoesntExist() {
        var expected = new CRUDException(ECRUDException.DOES_NOT_EXIST,
                FireStation.builder()
                        .station(testFireStation.getStation())
                        .build());
        var actual = assertThrows(CRUDException.class, () -> {
            testController.deleteFireStation(testFireStation.getStation());
        });

        assertEquals(expected, actual);
    }
}
