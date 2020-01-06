package com.example.demo.controller;

import com.example.demo.dto.userMenu.UserMenuRequestDTO;
import com.example.demo.service.UserMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.constants.WebResourceKeyConstants.API_V1;
import static com.example.demo.constants.WebResourceKeyConstants.SidebarConstants.BASE_SIDE_BAR;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1)
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

    @PutMapping(BASE_SIDE_BAR)
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
