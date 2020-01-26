package com.cogent.cogentserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Slf4j
public class CogentController {

    @GetMapping("/test")
    public String cogent() {
//        log.info("::::: COGENT SERVER IS RUNNING :::::: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());


        return "helloworld";

    }
    
}
