package com.cogent.admin.dto.department;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.SubDepartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-07-29
 */
public class DepartmentTestUtils {

    /*FOR SAVE*/
    public static DepartmentRequestDTO getDepartmentRequestDTO() {
        return DepartmentRequestDTO.builder()
                .name("Surgical")
                .code("SRG")
                .status('Y')
                .build();
    }
    public static DepartmentSearchRequestDTO getSearchDepartmentRequestDTO() {
        return DepartmentSearchRequestDTO.builder()
                .id(null)
                .name("Surgical")
                .code("SRG")
                .status('Y')
                .build();
    }

    public static DepartmentUpdateRequestDTO getupdatedDepartmentRequestDTO() {
        return DepartmentUpdateRequestDTO.builder()
                .id(1L)
                .name("Surgical")
                .code("SRG")
                .status('Y')
                .remarks("updateDepartment")
                .build();
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("I want to deleteDepartment")
                .build();
    }

    public static List<Long> getIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        return ids;
    }

    public static DepartmentMinimalResponseDTO getDepartmentMinimalReponseDTO() {
        return DepartmentMinimalResponseDTO.builder()
                .id(1L)
                .name("Surgical")
                .code("SRG")
                .status('Y')
                .build();
    }

    public static DepartmentResponseDTO getDepartmentReponseDTO() {
        return DepartmentResponseDTO.builder()
               .remarks("Test")
                .build();
    }

    public static DepartmentDropDownDTO getDepartmentDropDownDTO() {
        return DepartmentDropDownDTO.builder()
                .value(1L)
                .label("Surgical")
                .build();
    }

    public static Department getDepartmentInfo() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Surgical");
        department.setCode("SRG");
        department.setStatus('Y');
        department.setCreatedDate(null);
        department.setCreatedById(1L);
        department.setLastModifiedDate(null);
        department.setModifiedById(1L);
        department.setRemarks(null);
        return department;
    }


    public static List<DepartmentMinimalResponseDTO> departmentResponseDTOS() {
        List<DepartmentMinimalResponseDTO> departmentMinimalResponseDTOS = Arrays
                .asList(getDepartmentMinimalReponseDTO());
        return departmentMinimalResponseDTOS;
    }

    public static List<DepartmentDropDownDTO> departmentDropDownDTOS() {
        List<DepartmentDropDownDTO> departmentDropDownDTOS = Arrays
                .asList(getDepartmentDropDownDTO());
        return departmentDropDownDTOS;
    }


    public static SubDepartment getSubDepartmentInfo() {
        SubDepartment subDepartment = new SubDepartment();
        subDepartment.setId(2L);
        subDepartment.setName("Billing");
        subDepartment.setCode("BILL");
        subDepartment.setStatus('Y');
        subDepartment.setDepartment(getDepartmentInfo());
        return subDepartment;
    }

    public static List<SubDepartment> subDepartmentList() {
        List<SubDepartment> subDepartmentList = Arrays
                .asList(getSubDepartmentInfo());
        return subDepartmentList;
    }

    public static List<DeleteRequestDTO> getDeleteRequestDTOS(){
        return Arrays.asList(getDeleteRequestDTO());
    }

    public static List<Object[]> getDepartmentObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Surgical";
        object[1] = "SRG";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDepartmentObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "SRG";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDepartmentObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Surgical";
        objects.add(object);
        return objects;
    }
}
