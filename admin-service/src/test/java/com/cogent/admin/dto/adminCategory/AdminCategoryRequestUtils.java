package com.cogent.admin.dto.adminCategory;

import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;

import java.util.ArrayList;
import java.util.List;

import static com.cogent.admin.constants.StatusConstants.ACTIVE;

/**
 * @author smriti on 2019-08-11
 */
public class AdminCategoryRequestUtils {

    public static AdminCategoryRequestDTO getInvalidInputForSave() {
        return AdminCategoryRequestDTO.builder()
                .name("")
                .status('M')
                .build();
    }

    public static AdminCategoryRequestDTO getAdminCategoryRequestDTO() {
        return AdminCategoryRequestDTO.builder()
                .name("Doctor")
                .code("CT")
                .designation("Doctor")
                .registrationNumber("123")
                .status(ACTIVE)
                .build();
    }

    public static AdminCategoryUpdateRequestDTO getInvalidInputForUpdate() {
        return AdminCategoryUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    public static List<Object[]> getAdminCategoryObject() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Doctor";
        object[1] = "CT";
        objects.add(object);
        return objects;
    }

    public static AdminCategoryUpdateRequestDTO getAdminCategoryRequestDTOForUpdate() {
        return AdminCategoryUpdateRequestDTO.builder()
                .id(1L)
                .name("Doctor")
                .code("CT")
                .designation("Doctor edited")
                .registrationNumber("123")
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static AdminCategorySearchRequestDTO getAdminCategoryRequestDTOForSearch() {
        return AdminCategorySearchRequestDTO.builder()
                .id(1L)
                .code("CT")
                .designation("Doctor")
                .status('Y')
                .build();
    }
}
