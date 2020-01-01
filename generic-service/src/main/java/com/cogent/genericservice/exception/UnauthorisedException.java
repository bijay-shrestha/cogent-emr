package com.cogent.genericservice.exception;

import lombok.Getter;

import static com.cogent.genericservice.exception.utils.ExceptionUtils.getLocalDateTime;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public class UnauthorisedException extends RuntimeException {

    private ExceptionResponse exception;

    public UnauthorisedException(String errorMessage) {
        super(errorMessage);
        setErrorResponse(errorMessage);
    }

    private void setErrorResponse(String errorMessage) {
        exception = ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .debugMessage(errorMessage)
                .status(UNAUTHORIZED)
                .timeStamp(getLocalDateTime())
                .build();
    }
}
