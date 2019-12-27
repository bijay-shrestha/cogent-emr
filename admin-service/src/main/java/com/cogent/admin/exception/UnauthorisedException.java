package com.cogent.admin.exception;

import lombok.Getter;

import static com.cogent.admin.exception.utils.ExceptionUtils.generateMessage;
import static com.cogent.admin.exception.utils.ExceptionUtils.getLocalDateTime;
import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
public class UnauthorisedException extends RuntimeException {

    private ExceptionResponse exception;

    public UnauthorisedException(Class clazz, String debugMessage) {
        super(generateMessage(clazz));
        exception = ExceptionResponse.builder()
                .errorMessage(generateMessage(clazz))
                .debugMessage(debugMessage)
                .status(CONFLICT)
                .timeStamp(getLocalDateTime())
                .build();

    }

}
