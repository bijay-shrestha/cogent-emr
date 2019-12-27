package com.cogent.admin.dto.applicationModules;

import com.cogent.admin.dto.request.applicationModules.ApplicationModuleRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleUpdateRequestDTO;

/**
 * @author smriti ON 25/12/2019
 */

public class ApplicationModuleRequestUtils {

    public static ApplicationModuleRequestDTO getApplicationModuleRequestDTO() {
        return ApplicationModuleRequestDTO.builder()
                .name("ADMIN MODULE")
                .subDepartmentId(1L)
                .status('Y')
                .build();
    }

    public static ApplicationModuleUpdateRequestDTO getApplicationModuleRequestDTOForUpdate() {
        return ApplicationModuleUpdateRequestDTO.builder()
                .id(1L)
                .name("ADMIN MODULE")
                .subDepartmentId(1L)
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static ApplicationModuleSearchRequestDTO getApplicationModuleRequestDTOForSearch() {
        return ApplicationModuleSearchRequestDTO.builder()
                .name("ADMIN")
                .subDepartmentId(1L)
                .build();
    }
}
