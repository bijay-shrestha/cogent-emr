package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.persistence.model.SubDepartment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("subDepartmentRepositoryCustom")
public interface SubDepartmentRepositoryCustom {

    List<Object[]> fetchSubDepartmentByNameAndCode(String name, String code);

    List<Object[]> checkSubDepartmentNameAndCodeIfExist(Long id,String name, String code);

    List<SubDepartment> fetchSubDepartmentByDepartmentId(Long departmentId);

    List<DeleteRequestDTO> fetchSubDepartmentToDeleteByDepartmentId(Long departmentId);

    Optional<SubDepartment> fetchSubDepartmentById(Long id);

    Optional<SubDepartment> fetchActiveSubDepartmentByIdAndDepartmentId(Long id,Long departmentId);

    List<SubDepartmentMinimalResponseDTO> searchSubDepartment(
            SubDepartmentSearchRequestDTO subDepartmentSearchRequestDTO, Pageable pageable);

    Optional<List<SubDepartmentDropDownDTO>> fetchDropDownList();

    Optional<List<SubDepartmentDropDownDTO>> fetchActiveDropDownList();

    Optional<List<SubDepartmentDropDownDTO>> fetchActiveDropDownListByDepartmentId(Long departmentId);

    List<Object[]> getSubDepartment();

    Optional<SubDepartmentResponseDTO> fetchSubDepartmentDetail(Long id);



}
