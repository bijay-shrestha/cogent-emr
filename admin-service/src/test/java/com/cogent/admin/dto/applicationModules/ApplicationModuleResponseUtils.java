package com.cogent.admin.dto.applicationModules;

import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDetailResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDropdownResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import com.cogent.persistence.model.ApplicationModule;

import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.getSubDepartmentInfo;

/**
 * @author smriti ON 25/12/2019
 */

public class ApplicationModuleResponseUtils {

    public static ApplicationModule getApplicationModule() {
        return new ApplicationModule(1L, "ADMIN MODULE","ADM", getSubDepartmentInfo(), 'Y', null);
    }

    public static ApplicationModule getUpdatedApplicationModule() {
        return new ApplicationModule(1L, "ADMIN MODULE", "ADM",getSubDepartmentInfo(),
                'N', "yes. Inactive it!");
    }

    public static ApplicationModule getDeletedApplicationModuleInfo() {
        return new ApplicationModule(1L, "ADMIN MODULE","ADM", getSubDepartmentInfo(),
                'D', "Yes, delete!!");
    }

    public static List<ApplicationModuleMinimalResponseDTO> fetchMinimalApplicationModuleList() {
        return Arrays.asList(ApplicationModuleMinimalResponseDTO.builder()
                        .id(1L)
                        .name("ADMIN MODULE")
                        .subDepartmentName("ADMIN")
                        .status('Y')
                        .build(),
                ApplicationModuleMinimalResponseDTO.builder()
                        .id(2L)
                        .name("PHARMACY MODULE")
                        .subDepartmentName("PHARMACY")
                        .status('Y')
                        .build()
        );
    }

    public static List<ApplicationModuleDropdownResponseDTO> fetchApplicationModuleListForDropDown() {
        return Arrays.asList(ApplicationModuleDropdownResponseDTO.builder()
                        .id(1L)
                        .name("ADMIN MODULE")
                        .subDepartmentId(1L)
                        .build(),
                ApplicationModuleDropdownResponseDTO.builder()
                        .id(2L)
                        .name("PHARMACY MODULE")
                        .subDepartmentId(2L)
                        .build()
        );
    }

    public static ApplicationModuleDetailResponseDTO fetchApplicationModuleDetailById() {
        return ApplicationModuleDetailResponseDTO.builder()
                .name("ADMIN MODULE")
                .subDepartmentName("ADMIN")
                .subDepartmentId(1L)
                .status('Y')
                .remarks("delete it").build();
    }
}
