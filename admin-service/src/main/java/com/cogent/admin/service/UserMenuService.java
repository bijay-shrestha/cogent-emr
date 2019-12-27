package com.cogent.admin.service;

import com.cogent.admin.dto.request.login.UserMenuRequestDTO;
import com.cogent.admin.dto.response.login.AssignedProfileMenuResponseDTO;

/**
 * @author smriti ON 27/12/2019
 */

public interface UserMenuService {
    AssignedProfileMenuResponseDTO fetchAssignedProfileMenuResponseDto(UserMenuRequestDTO userMenuRequestDTO);
}
