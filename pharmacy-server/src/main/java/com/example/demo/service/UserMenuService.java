package com.example.demo.service;

import com.example.demo.dto.userMenu.AssignedProfileMenuResponseDTO;
import com.example.demo.dto.userMenu.UserMenuRequestDTO;

/**
 * @author smriti ON 27/12/2019
 */

public interface UserMenuService {
    AssignedProfileMenuResponseDTO fetchAssignedProfileMenuResponseDto(UserMenuRequestDTO userMenuRequestDTO);
}
