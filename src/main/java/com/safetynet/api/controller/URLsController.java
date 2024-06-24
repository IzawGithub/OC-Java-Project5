package com.safetynet.api.controller;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.helper.IStackLog;
import com.safetynet.api.model.dto.DataChildAlert;
import com.safetynet.api.model.dto.DataFire;
import com.safetynet.api.model.dto.DataFlood;
import com.safetynet.api.model.dto.DataInfo;
import com.safetynet.api.model.dto.DataServedByFireStation;
import com.safetynet.api.service.URLsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class URLsController implements IStackLog {
    // Public

    @GetMapping("childAlert")
    @JsonView(DataChildAlert.URLsControllerView.class)
    public ResponseEntity<List<DataChildAlert>> getChildAlert(@RequestParam String address) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getChildAlert(address), HttpStatus.OK);
    }

    @GetMapping("communityEmail")
    public ResponseEntity<Set<String>> getCommunityEmail(@RequestParam String city) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getCommunityEmail(city), HttpStatus.OK);
    }

    @GetMapping("fire")
    @JsonView(DataFire.URLsControllerView.class)
    public ResponseEntity<List<DataFire>> getFire(@RequestParam String address) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getFire(address), HttpStatus.OK);
    }

    @GetMapping("fireStation")
    @JsonView(DataServedByFireStation.URLsControllerView.class)
    public ResponseEntity<DataServedByFireStation> getServedByFireStation(@RequestParam String station) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getServedByFireStation(station), HttpStatus.OK);
    }

    @GetMapping("flood")
    @JsonView(DataFlood.URLsControllerView.class)
    public ResponseEntity<List<DataFlood>> getFlood(@RequestParam List<String> stations) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getFlood(stations), HttpStatus.OK);
    }

    @GetMapping("personInfo")
    @JsonView(DataInfo.URLsControllerView.class)
    public ResponseEntity<List<DataInfo>> getPersonInfo(@RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getPersonInfo(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping("phoneAlert")
    public ResponseEntity<Set<String>> getPhoneAlert(@RequestParam String firestation) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(urlsService.getPhoneAlert(firestation), HttpStatus.OK);
    }

    // Private

    // -- Interfaces --

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final URLsService urlsService;
}
