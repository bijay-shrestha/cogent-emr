package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.persistence.model.Department;

import java.util.Date;
import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi
 */

public class DepartmentUtils {
    public static Department parseToDepartment(DepartmentRequestDTO departmentRequestDTO) {
        departmentRequestDTO.setName(toUpperCase(departmentRequestDTO.getName()));
        departmentRequestDTO.setCode(toUpperCase(departmentRequestDTO.getCode()));
        return MapperUtility.map(departmentRequestDTO, Department.class);
    }

    public static BiFunction<Department, DeleteRequestDTO, Department> updateDepartment =
            (departmentToDelete, deleteRequestDTO) -> {
                departmentToDelete.setStatus(deleteRequestDTO.getStatus());
                departmentToDelete.setRemarks(deleteRequestDTO.getRemarks());
                departmentToDelete.setLastModifiedDate(new Date());
                departmentToDelete.setModifiedById(1L);
                return departmentToDelete;
            };
}