package com.safetynet.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.safetynet.api.model.Person;

@SpringBootTest(useMainMethod = UseMainMethod.ALWAYS, webEnvironment = WebEnvironment.RANDOM_PORT)
class SmokeTests {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;
    HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUpPerTest() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void testGetPerson() {
        var expected = Person.builder()
                .firstName("John")
                .lastName("Boyd")
                .build();

        var actual = restTemplate.exchange(
                createUrl(MessageFormat.format("/person/{0}-{1}", expected.getFirstName(), expected.getLastName())),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                Person.class).getBody();
        assertEquals(expected, actual);
    }

    private String createUrl(String uri) {
        return MessageFormat.format("http://localhost:{0,number,#}{1}", port, uri);
    }

}
