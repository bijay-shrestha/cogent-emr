package com.cogent.admin.service;

import com.cogent.admin.dto.request.userMenu.UserMenuRequestDTO;
import com.cogent.admin.dto.response.userMenu.AssignedProfileResponseDTO;

/**
 * @author smriti ON 27/12/2019
 */

public interface UserMenuService {
    AssignedProfileResponseDTO fetchAssignedProfileMenuResponseDto(UserMenuRequestDTO userMenuRequestDTO);
}
