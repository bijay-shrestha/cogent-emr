package com.cogent.edgeserver.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException {

    private ErrorResponse exception;

    public NotFoundException(String message) {
        super(generateMessage());
        exception = ErrorResponse.builder()
                .message(generateMessage())
                .status(HttpStatus.NOT_FOUND)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    private static String generateMessage() {
        return  "Authorised Page Not Found...";
    }

}
