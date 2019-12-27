package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi
 */

@Repository
@Qualifier("departmentRepositoryCustom")
public interface DepartmentRepositoryCustom {

    DepartmentResponseDTO fetchDepartmentDetails(Long id);

    List<Object[]> fetchDepartmentByNameOrCode(String name, String code);

    List<DepartmentMinimalResponseDTO> searchDepartment(
            DepartmentSearchRequestDTO departmentSearchRequestDTO, Pageable pageable);

    Optional<List<DepartmentDropDownDTO>> fetchDropDownList();

    Optional<List<DepartmentDropDownDTO>> fetchActiveDropDownList();

    List<Object[]> getDepartment();

    List<Object[]> checkIfDepartmentNameAndCodeExists(Long id, String name, String code);
}
