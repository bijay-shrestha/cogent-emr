package com.cogent.admin.exception;

import lombok.Getter;

import static com.cogent.admin.exception.utils.ExceptionUtils.getLocalDateTime;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class FileNotFoundException extends RuntimeException {
    private ExceptionResponse exception;

    public FileNotFoundException(String errorMessage, String debugMessage) {
        super(errorMessage);
        setErrorResponse(errorMessage, debugMessage);
    }

    private void setErrorResponse(String errorMessage, String debugMessage) {
        exception = ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .status(NOT_FOUND)
                .timeStamp(getLocalDateTime())
                .build();
    }
}