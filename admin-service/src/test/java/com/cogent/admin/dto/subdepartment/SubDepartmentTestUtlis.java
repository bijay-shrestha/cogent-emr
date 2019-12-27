package com.cogent.admin.dto.subdepartment;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.SubDepartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubDepartmentTestUtlis {

    public static SubDepartmentRequestDTO getSubDepartmentRequestDTO() {
        return SubDepartmentRequestDTO.builder()
                .name("EAR")
                .code("ENT-EAR")
                .status('Y')
                .departmentId(16L)
                .build();
    }

    public static List<Object[]> getSubDepartmentObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "EAR";
        object[1] = "ENT-EAR";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getSubDepartmentObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "ENT-EAR";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getSubDepartmentObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "EAR";
        objects.add(object);
        return objects;
    }

    public static SubDepartmentUpdateRequestDTO getsubDepartmentUpdateRequestDTO() {
        return SubDepartmentUpdateRequestDTO.builder()
                .id(4L)
                .name("EAR")
                .code("ENT-EAR")
                .status('Y')
                .departmentId(16L)
                .remarks("update")
                .build();
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("I want to deleteDepartment")
                .build();
    }

    public static SubDepartmentMinimalResponseDTO getSubDepartmentMinimalResponseDTO() {
        return SubDepartmentMinimalResponseDTO.builder()
                .id(4L)
                .name("EAR")
                .code("ENT-EAR")
                .status('Y')
                .department("ENT")
                .build();
    }

    public static SubDepartmentDropDownDTO getSubDepartmentDropDownDTO() {
        return SubDepartmentDropDownDTO.builder()
                .value(4L)
                .label("EAR")
                .build();
    }

    public static SubDepartmentResponseDTO getSubDepartmentResponseDTO() {
        return SubDepartmentResponseDTO.builder()
                .remarks("test")
                .build();
    }

    public static SubDepartment getSubDepartmentInfo() {
        SubDepartment subDepartment = new SubDepartment();
        subDepartment.setId(1L);
        subDepartment.setDepartment(getDepartmentInfo());
        subDepartment.setName("EAR");
        subDepartment.setCode("ENT-EAR");
        subDepartment.setStatus('Y');
        return subDepartment;
    }

    public static SubDepartment getDeletedSubDepartmentInfo() {
        SubDepartment subDepartment = new SubDepartment();
        subDepartment.setId(4L);
        subDepartment.setDepartment(getDepartmentInfo());
        subDepartment.setName("EAR");
        subDepartment.setCode("ENT-EAR");
        subDepartment.setStatus('D');
        subDepartment.setRemarks("I want to deleted");
        return subDepartment;
    }

    public static Department getDepartmentInfo() {
        Department department = new Department();
        department.setId(16L);
        department.setName("Ear nose and throat");
        department.setCode("ENT");
        department.setStatus('Y');
        return department;
    }

    public static List<SubDepartmentMinimalResponseDTO> SubDepartmentMinimalResponseDTO() {
        List<SubDepartmentMinimalResponseDTO> responseDTOS = Arrays
                .asList(getSubDepartmentMinimalResponseDTO());
        return responseDTOS;
    }

    public static List<SubDepartmentDropDownDTO> SubDepartmentDropDownDTO() {
        List<SubDepartmentDropDownDTO> responseDTOS = Arrays
                .asList(getSubDepartmentDropDownDTO());
        return responseDTOS;
    }

}
