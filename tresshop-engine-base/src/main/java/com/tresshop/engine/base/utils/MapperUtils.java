package com.tresshop.engine.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MapperUtils {

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(MapperUtils.class);


    public String getJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("exception {} while getting json string and message is  {}", e, e.getMessage());
            return null;
        }
    }

    public <T> T getObjectFromJsonString(String jsonString, TypeReference type) {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            logger.error("exception {} while getting json string and message is  {}", e, e.getMessage());
            return null;
        }
    }

    public <T> T mapObjectToType(Object object, TypeReference type) {
        try {
            return objectMapper.convertValue(object, type);
        } catch (IllegalArgumentException e) {
            logger.error("exception {} while getting json string and message is  {}", e, e.getMessage());
            return null;
        }
    }
}
