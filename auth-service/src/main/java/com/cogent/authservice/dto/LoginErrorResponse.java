package com.cogent.authservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginErrorResponse {
    private int status;
    private String message;
    private String token;

}
