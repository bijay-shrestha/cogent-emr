package com.cogent.admin.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;

import static com.cogent.admin.exception.utils.ExceptionUtils.*;
import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
public class DataDuplicationException extends RuntimeException {

    private ExceptionResponse exception;

    public DataDuplicationException(Class clazz, String debugMessage) {
        super(generateMessage(clazz));
        setErrorResponse(generateMessage(clazz), debugMessage);
    }

    public DataDuplicationException(String errorMessage) {
        super(errorMessage);
        setErrorResponse(errorMessage, errorMessage);
    }

    public DataDuplicationException(Class clazz, String errorMessage, String debugMessage) {
        super(generateMessage(clazz, errorMessage));
        setErrorResponse(generateMessage(clazz, errorMessage), generateDebugMessage(clazz, debugMessage));
    }

    private void setErrorResponse(String errorMessage, String debugMessage) {
        exception = ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .status(CONFLICT)
                .timeStamp(getLocalDateTime())
                .build();
    }

    public DataDuplicationException(String clazz, String searchParamsMap) {
        super(generateMessage(clazz, toMap(String.class, String.class, searchParamsMap)));
        setErrorResponse(
                (generateMessage(clazz, toMap(String.class, String.class, searchParamsMap))),
                StringUtils.capitalize("Object returned empty or null for ")
                        + toMap(String.class, String.class, searchParamsMap));
    }
}
