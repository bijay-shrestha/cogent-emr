package com.cogent.edgeserver.exception;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public class InvalidCredentialException extends RuntimeException {

    private ErrorResponse exception;

    public InvalidCredentialException(String message) {
        super(generateMessage());
        exception = ErrorResponse.builder()
                .message(generateMessage())
                .status(UNAUTHORIZED)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    private static String generateMessage() {
        return  "Invalid Credentails";
    }

}