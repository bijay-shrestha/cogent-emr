package com.cogent.genericservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author smriti ON 02/01/2020
 */

public class ObjectToJSONUtils {

    public static <T> String writeObjectToJson(T requestDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestDTO);
    }
}
