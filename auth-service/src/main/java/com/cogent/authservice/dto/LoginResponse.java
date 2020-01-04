package com.cogent.authservice.dto;

import lombok.*;

import javax.servlet.http.Cookie;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

//    private int status;
//    private String message;
//    private String token;
    private Cookie cookie;
}
