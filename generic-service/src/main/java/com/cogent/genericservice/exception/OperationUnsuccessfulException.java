package com.cogent.genericservice.exception;

import lombok.Getter;

import static com.cogent.genericservice.exception.utils.ExceptionUtils.getLocalDateTime;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

/**
 * @author smriti on 2019-09-01
 */
@Getter
public class OperationUnsuccessfulException extends RuntimeException {
    private ExceptionResponse exception;

    public OperationUnsuccessfulException(String errorMessage) {
        super(errorMessage);
        setErrorResponse(errorMessage, errorMessage);
    }

    public OperationUnsuccessfulException(String errorMessage, String debugMessage) {
        super(errorMessage);
        setErrorResponse(errorMessage, debugMessage);
    }

    private void setErrorResponse(String errorMessage, String debugMessage) {
        exception = ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .status(EXPECTATION_FAILED)
                .timeStamp(getLocalDateTime())
                .build();
    }
}
