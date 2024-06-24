package com.safetynet.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.helper.IStackLog;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class PersonController implements IStackLog {
    // Public

    // -- Create --

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(personService.create(person), HttpStatus.OK);
    }

    // -- Read --

    @GetMapping("/{firstName}-{lastName}")
    public ResponseEntity<Person> getPerson(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(personService.getPerson(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
    }

    // -- Update --

    @PutMapping("/{firstName}-{lastName}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatePerson) throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(personService.updatePerson(firstName, lastName, updatePerson), HttpStatus.OK);
    }

    // -- Delete --

    @DeleteMapping("/{firstName}-{lastName}")
    public ResponseEntity<Person> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) throws CRUDException {
        logger.trace(getCurrentMethod());
        return new ResponseEntity<>(personService.deletePerson(firstName, lastName), HttpStatus.OK);
    }

    // Private

    // -- Interfaces --

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final PersonService personService;
}
