package com.safetynet.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.helper.IStackLog;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.dto.DataUpdateFireStation;
import com.safetynet.api.service.FireStationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class FireStationController implements IStackLog {
    // Public

    @GetMapping("/{station}")
    public ResponseEntity<FireStation> getFireStation(@PathVariable String station) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(fireStationService.getFireStation(station), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(fireStationService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation)
            throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(fireStationService.create(fireStation), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FireStation> updateFireStation(@RequestBody DataUpdateFireStation payload)
            throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(fireStationService.updateFireStation(payload), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<FireStation> deleteFireStation(@PathVariable String station) throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(fireStationService.deleteFireStation(station), HttpStatus.OK);
    }

    // Private

    // -- Interface --

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final FireStationService fireStationService;

}
