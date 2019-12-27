package com.cogent.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author smriti on 2019-08-11
 */
public class ObjectToJSONUtils {

    public static <T> String writeObjectToJson(T requestDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestDTO);
    }
}
