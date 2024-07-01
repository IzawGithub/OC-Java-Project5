package com.safetynet.api.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.CRUDRepository;
import com.safetynet.api.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService implements CRUDService<Person> {
    // Public

    // -- Read --

    public Person getPerson(String firstName, String lastName) {
        var person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return get(person);
    }

    // -- Update --

    public Person updatePerson(String firstName, String lastName, Person updatePerson) throws CRUDException {
        var person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return update(person, updatePerson);
    }

    // -- Delete --

    public Person deletePerson(String firstName, String lastName) throws CRUDException {
        var person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return delete(person);
    }

    // Private

    // -- Overrides --

    @Override
    public CRUDRepository<Person> getRepository() {
        return personRepository;
    }

    // -- Interfaces --

    private final PersonRepository personRepository;

}
