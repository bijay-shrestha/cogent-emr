package com.cogent.edgeserver.exception;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@Slf4j
public class InvalidCredentialException extends RuntimeException {

    private ErrorResponse exception;

    public InvalidCredentialException(String message) {
        super(generateMessage());
        log.info("These are invalid credential.");
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