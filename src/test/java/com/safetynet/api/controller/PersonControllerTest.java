package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.exception.CRUDException.ECRUDException;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.PersonService;

class PersonControllerTest {
    final Person testPerson = Person.builder()
            .firstName("John")
            .lastName("Doe")
            .address("1 JohnDoeStreets")
            .city("JohnDoeCity")
            .zip("1337")
            .phone("012-34-5678")
            .email("john.doe@johndoe.com")
            .build();

    PersonRepository testRepository;
    PersonService testService;
    PersonController testController;

    @BeforeEach
    void setUpPerTest() {
        testRepository = new PersonRepository("{}");
        testService = new PersonService(testRepository);
        testController = new PersonController(testService);
    }

    // Create

    @Test
    void createPerson() throws CRUDException {
        var expected = testPerson;

        var actual = testController.createPerson(expected).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void createPersonAlreadyExist() throws CRUDException {
        testController.createPerson(testPerson);
        var expected = new CRUDException(ECRUDException.ALREADY_EXIST, testPerson);

        var actual = assertThrows(CRUDException.class, () -> testController.createPerson(testPerson));
        assertEquals(expected, actual);
    }

    // Read

    @Test
    void getPerson() throws CRUDException {
        var expected = testPerson;
        testController.createPerson(expected);

        var actual = testController.getPerson(expected.getFirstName(), expected.getLastName()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void getPersonDoesNotExist() {
        Person expected = null;

        var actual = testController.getPerson(testPerson.getFirstName(), testPerson.getLastName()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void getAllPersons() throws CRUDException {
        List<Person> expected = List.of(testPerson);
        testController.createPerson(testPerson);

        var actual = testController.getAllPersons().getBody();
        assertEquals(expected, actual);
    }

    // Update

    @Test
    void updatePerson() throws CRUDException {
        var expected = Person.builder()
                .firstName("Jahn")
                .lastName("Doe")
                .build();
        testController.createPerson(testPerson);

        var actual = testController.updatePerson(testPerson.getFirstName(), testPerson.getLastName(), expected)
                .getBody();
        assertEquals(expected, actual);
        assertNotEquals(testPerson, actual);
    }

    @Test
    void updatePersonDoesntExist() {
        Person updatePerson = Person.builder()
                .firstName("Jahn")
                .lastName("Doe")
                .build();

        var expected = new CRUDException(ECRUDException.DOES_NOT_EXIST, testPerson);
        var actual = assertThrows(CRUDException.class,
                () -> testController.updatePerson(testPerson.getFirstName(), testPerson.getLastName(), updatePerson));

        assertEquals(expected, actual);
    }

    // Delete

    @Test
    void deletePerson() throws CRUDException {
        var expected = testPerson;
        testController.createPerson(expected);

        var actual = testController.deletePerson(expected.getFirstName(), expected.getLastName()).getBody();
        assertEquals(expected, actual);
    }

    @Test
    void deletePersonThatDoesntExist() {
        var expected = new CRUDException(ECRUDException.DOES_NOT_EXIST, testPerson);
        var actual = assertThrows(CRUDException.class,
                () -> testController.deletePerson(testPerson.getFirstName(), testPerson.getLastName()));

        assertEquals(expected, actual);
    }

}
