package com.cogent.cogentserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CogentController {

    @GetMapping("/")
    public ResponseEntity<?> cogent() {
        System.out.println("SERVER IS RUNNING");
        return ok("Server is Running-----");
    }
    
}
