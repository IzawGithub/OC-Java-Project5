package com.safetynet.api;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class ApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    public static void main(String[] args) {
        logger.info("Spring {}", SpringVersion.getVersion());
        logger.info("Starting SafetyNet");
        SpringApplication.run(ApiApplication.class, args);
    }

}
