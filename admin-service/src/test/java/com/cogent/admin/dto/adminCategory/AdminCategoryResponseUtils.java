package com.cogent.admin.dto.adminCategory;

import com.cogent.admin.dto.response.adminCategory.AdminCategoryDropdownDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryResponseDTO;
import com.cogent.persistence.model.AdminCategory;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-08-23
 */
public class AdminCategoryResponseUtils {
    public static AdminCategory getAdminCategory() {
        return new AdminCategory(1L, "Doctor", "CT", "Doctor", "123",
                'Y', null);
    }

    public static AdminCategory getUpdatedAdminCategory() {
        return new AdminCategory(1L, "Doctor", "CT", "Doctor edited",
                "123", 'N', "yes. Inactive it!");
    }

    /*FOR DELETE*/
    public static AdminCategory getDeletedAdminCategoryInfo() {
        return new AdminCategory(2L, "Doctor", "CT", "Doctor", "1234",
                'D', "Yes, delete!!");
    }

    public static List<AdminCategoryMinimalResponseDTO> fetchMinimalAdminCategoryList() {
        return Arrays.asList(AdminCategoryMinimalResponseDTO.builder()
                        .id(1L)
                        .name("Doctor")
                        .code("CT")
                        .designation("Doctor")
                        .registrationNumber("1234")
                        .status('Y')
                        .build(),
                AdminCategoryMinimalResponseDTO.builder()
                        .id(2L)
                        .name("IT ADMINISTRATOR")
                        .code("AT")
                        .designation("Administrator")
                        .registrationNumber("56")
                        .status('Y')
                        .build()
        );
    }

    public static List<AdminCategoryDropdownDTO> fetchAdminCategoryListForDropDown() {
        return Arrays.asList(AdminCategoryDropdownDTO.builder()
                        .value(1L)
                        .label("Doctor")
                        .build(),
                AdminCategoryDropdownDTO.builder()
                        .value(2L)
                        .label("IT ADMINISTRATOR")
                        .build()
        );
    }

    public static AdminCategoryResponseDTO fetchAdminCategoryDetailById() {
        return AdminCategoryResponseDTO.builder().remarks("delete it").build();
    }
}
