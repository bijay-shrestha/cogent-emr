package com.cogent.genericservice.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;

import static com.cogent.genericservice.exception.utils.ExceptionUtils.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class NoContentFoundException extends RuntimeException {

    private ExceptionResponse exception;

    public NoContentFoundException(String errorMessage) {
        super(errorMessage);
        setErrorResponse(errorMessage, errorMessage);
    }

    public NoContentFoundException(Class clazz) {
        super(generateMessage(clazz));
        setErrorResponse(generateMessage(clazz), generateDebugMessage(clazz));
    }

    private void setErrorResponse(String errorMessage, String debugMessage) {
        exception = ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .status(NOT_FOUND)
                .timeStamp(getLocalDateTime())
                .build();
    }

    public NoContentFoundException(Class clazz, String... searchParamsMap) {
        super(generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
        setErrorResponse(
                generateMessage(clazz),
                StringUtils.capitalize("Object returned empty or null for ")
                        + toMap(String.class, String.class, searchParamsMap));
    }

    public NoContentFoundException(String errorMessage, String... searchParamsMap) {
        super(generateMessage(errorMessage, toMap(String.class, String.class, searchParamsMap)));
        setErrorResponse(errorMessage,
                StringUtils.capitalize("Object returned empty or null for ")
                        + toMap(String.class, String.class, searchParamsMap));
    }
}