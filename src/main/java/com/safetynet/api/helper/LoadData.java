package com.safetynet.api.helper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class LoadData implements IStackLog {
    public String loadDataJson() {
        String result = null;
        try {
            result = IOUtils.toString(jsonFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("'{}' error: '{}'", getCurrentMethod(), e);
        }
        return result;
    }

    // Private

    // -- Interface --

    @Value("classpath:static/data.json")
    private Resource jsonFile;
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
}
