package com.cogent.authservice.dto;

import lombok.*;

import javax.servlet.http.Cookie;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

//    private int status;
//    private String message;
//    private String token;
    private Cookie cookie;


}
