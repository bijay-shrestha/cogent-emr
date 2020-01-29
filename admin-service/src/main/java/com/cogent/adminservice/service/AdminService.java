package com.cogent.adminservice.service;


import com.cogent.adminservice.dto.response.AssignedProfileMenuResponseDTO;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.ProfileMenu;

import java.util.List;

public interface AdminService{

    List<ProfileMenu> getAdminByUsername(String t);
    AssignedProfileMenuResponseDTO getAssignedRoles(String t);
    Admin findAdminById(Long id);
}
