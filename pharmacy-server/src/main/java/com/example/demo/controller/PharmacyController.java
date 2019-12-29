package com.example.demo.controller;

import com.example.demo.dto.userMenu.UserMenuRequestDTO;
import com.example.demo.service.UserMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class PharmacyController {

    private final UserMenuService userMenuService;

    public PharmacyController(UserMenuService userMenuService) {
        this.userMenuService = userMenuService;
    }

//    @GetMapping("/")
//    public ResponseEntity<?> pharmacy() {
//
//        String pharmacy = "This is PHARMACY module------------------------------";
//        return ResponseEntity.ok(pharmacy);
//    }

    @PostMapping("/")
    public ResponseEntity<?> getUserMenuResponse(@RequestBody UserMenuRequestDTO requestDTO) {

        System.out.println(":::: PROFILE RESPONSE:::");
        return ok(userMenuService.fetchAssignedProfileMenuResponseDto(requestDTO));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {

        String login = "This is PHARMACY LOGIN PAGE-------------------";
        return ResponseEntity.ok(login);
    }

}
