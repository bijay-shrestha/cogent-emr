package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Sauravi
 */

public interface DepartmentService {

    void createDepartment(DepartmentRequestDTO departmentRequestDto);

    DepartmentResponseDTO fetchDepartmentDetails(Long id);

    void deleteDepartment(DeleteRequestDTO deleteRequestDTO);

    void updateDepartment(DepartmentUpdateRequestDTO departmentUpdateRequestDTO);

    List<DepartmentDropDownDTO> dropDownList();

    List<DepartmentDropDownDTO> activeDropDownList();

    List<DepartmentMinimalResponseDTO> searchDepartment(DepartmentSearchRequestDTO departmentSearchRequestDTO, Pageable pageable);

    void createExcelInByteArray(HttpServletResponse response) throws IOException;

}
