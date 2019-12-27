package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;
import com.cogent.persistence.model.AdminCategory;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-08-11
 */
public class AdminCategoryUtils {

    public static AdminCategory convertDTOToAdminCategory(AdminCategoryRequestDTO adminCategoryRequestDTO) {

        AdminCategory adminCategory = new AdminCategory();
        adminCategory.setName(toUpperCase(adminCategoryRequestDTO.getName()));
        adminCategory.setCode(adminCategoryRequestDTO.getCode());
        adminCategory.setDesignation(adminCategoryRequestDTO.getDesignation());
        adminCategory.setRegistrationNumber(adminCategoryRequestDTO.getRegistrationNumber());
        adminCategory.setStatus(adminCategoryRequestDTO.getStatus());
        return adminCategory;
    }

    public static AdminCategory convertToUpdatedAdminCategory(AdminCategoryUpdateRequestDTO updateRequestDTO,
                                                              AdminCategory adminCategory) {

        adminCategory.setName(toUpperCase(updateRequestDTO.getName()));
        adminCategory.setCode(updateRequestDTO.getCode());
        adminCategory.setDesignation(updateRequestDTO.getDesignation());
        adminCategory.setRegistrationNumber(updateRequestDTO.getRegistrationNumber());
        adminCategory.setStatus(updateRequestDTO.getStatus());
        adminCategory.setRemarks(updateRequestDTO.getRemarks());
        return adminCategory;
    }

    public static AdminCategory convertToDeletedAdminCategory(AdminCategory adminCategory,
                                                              DeleteRequestDTO deleteRequestDTO) {

        adminCategory.setStatus(deleteRequestDTO.getStatus());
        adminCategory.setRemarks(deleteRequestDTO.getRemarks());
        return adminCategory;
    }
}
