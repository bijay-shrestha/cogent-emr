package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleUpdateRequestDTO;
import com.cogent.persistence.model.ApplicationModule;
import com.cogent.persistence.model.SubDepartment;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti ON 24/12/2019
 */

public class ApplicationModuleUtils {

    public static ApplicationModule parseToApplicationModule(ApplicationModuleRequestDTO requestDTO,
                                                             SubDepartment subDepartment) {
        ApplicationModule applicationModule = new ApplicationModule();
        applicationModule.setName(toUpperCase(requestDTO.getName()));
        applicationModule.setStatus(requestDTO.getStatus());
        applicationModule.setSubDepartmentId(subDepartment);
        return applicationModule;
    }

    public static void parseToUpdatedApplicationModule(ApplicationModule applicationModule,
                                                       ApplicationModuleUpdateRequestDTO requestDTO,
                                                       SubDepartment subDepartment) {

        applicationModule.setName(toUpperCase(requestDTO.getName()));
        applicationModule.setStatus(requestDTO.getStatus());
        applicationModule.setRemarks(requestDTO.getRemarks());
        applicationModule.setSubDepartmentId(subDepartment);
    }

    public static void convertToDeletedApplicationModule(ApplicationModule applicationModule,
                                                         DeleteRequestDTO deleteRequestDTO) {

        applicationModule.setStatus(deleteRequestDTO.getStatus());
        applicationModule.setRemarks(deleteRequestDTO.getRemarks());
    }

}
