package com.cogent.cogentserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
@Slf4j
public class LogoutController {

    @GetMapping("/test")
    public String logout(){
        log.info("Server is logged out.");
        return "IAuthenticationFacade am loging out";
    }
}
