package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.request.login.UserMenuRequestDTO;
import com.cogent.admin.service.UserMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti ON 27/12/2019
 */
@RestController
public class UserMenuController {

    private final UserMenuService userMenuService;

    public UserMenuController(UserMenuService userMenuService) {
        this.userMenuService = userMenuService;
    }

    @PostMapping("/")
    public ResponseEntity<?> getUserMenuResponse(@RequestBody UserMenuRequestDTO requestDTO) {

        System.out.println(":::: PROFILE RESPONSE:::");
        return ok(userMenuService.fetchAssignedProfileMenuResponseDto(requestDTO));
    }
}
