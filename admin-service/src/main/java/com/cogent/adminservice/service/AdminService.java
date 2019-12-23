package com.cogent.adminservice.service;


import com.cogent.persistence.model.ProfileMenu;

import java.util.List;

public interface AdminService{

    List<ProfileMenu> getAdminByUsername(String t);
}