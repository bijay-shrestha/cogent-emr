package com.cogent.cogentserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CogentController {

    @GetMapping("/test")
    public String cogent() {
        log.info("::::: COGENT SERVER IS RUNNING :::::: ");

        return "helloworld";

    }
    
}
