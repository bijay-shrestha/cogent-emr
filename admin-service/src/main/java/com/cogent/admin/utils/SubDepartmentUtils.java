package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.SubDepartment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class SubDepartmentUtils {


    public static BiFunction<SubDepartmentRequestDTO, Department, SubDepartment> parseToSubDepartment =
            (subDepartmentRequestDTO, department) -> {
                SubDepartment subDepartment = new SubDepartment();
                subDepartment.setDepartment(department);
                subDepartment.setName(toUpperCase(subDepartmentRequestDTO.getName()));
                subDepartment.setCode(toUpperCase(subDepartmentRequestDTO.getCode()));
                subDepartment.setStatus(subDepartmentRequestDTO.getStatus());
                subDepartment.setCreatedDate(new Date());
                subDepartment.setCreatedById(1L);
                return subDepartment;
            };


    public static BiFunction<List<SubDepartment>, DepartmentUpdateRequestDTO, List<SubDepartment>> parseToUpdateListOfSubDepartment =
            (subDepartments, updateRequestDTO) -> {
                List<SubDepartment> subDepartmentList = new ArrayList<>();
                subDepartments.forEach(subDepartment -> {
                    subDepartment.setStatus(updateRequestDTO.getStatus());
                    subDepartment.setRemarks(updateRequestDTO.getRemarks());
                    subDepartment.setLastModifiedDate(new Date());
                    subDepartment.setModifiedById(1L);
                    subDepartmentList.add(subDepartment);
                });
                return subDepartmentList;
            };


    public static BiFunction<SubDepartment, DeleteRequestDTO, SubDepartment> updateSubDepartment =
            (subDepartment, deleteRequestDTO) -> {
                subDepartment.setStatus(deleteRequestDTO.getStatus());
                subDepartment.setRemarks(deleteRequestDTO.getRemarks());
                subDepartment.setLastModifiedDate(new Date());
                subDepartment.setModifiedById(1L);
                return subDepartment;
            };

    public static final BiFunction<DeleteRequestDTO, List<DeleteRequestDTO>, List<DeleteRequestDTO>>
            subDepartmentDeleteRequestDTO = (departmentDeleteRequestDTO, subDepartmentDeleteRequestDTOS) -> {
        subDepartmentDeleteRequestDTOS.forEach(subDepartmentUpdateRequestDTO -> {
            subDepartmentUpdateRequestDTO.setStatus(departmentDeleteRequestDTO.getStatus());
            subDepartmentUpdateRequestDTO.setRemarks(departmentDeleteRequestDTO.getRemarks());
        });
        return subDepartmentDeleteRequestDTOS;
    };
}
