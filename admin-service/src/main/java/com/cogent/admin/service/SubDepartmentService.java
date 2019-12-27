package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.persistence.model.SubDepartment;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface SubDepartmentService {

    void createSubDepartment(SubDepartmentRequestDTO subDepartmentRequestDTO);

    List<SubDepartmentMinimalResponseDTO> searchSubDepartment(
            SubDepartmentSearchRequestDTO subDepartmentSearchRequestDTO,
            Pageable pageable);

    void deleteSubDepartment(DeleteRequestDTO deleteRequestDTO);

    List<SubDepartmentDropDownDTO> dropDownList();

    List<SubDepartmentDropDownDTO> activeDropDownList();

    List<SubDepartmentDropDownDTO> activeDropDownListByDepartmentId(Long departmentId);

    void updateSubDepartment(SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO);

    void createExcelInByteArray(HttpServletResponse response) throws IOException;

    SubDepartment findById(Long subDepartmentId);

    SubDepartmentResponseDTO fetchSubDepartmentDetailsById(Long id);

    void checkAndUpdateProfile(Long subDepartmentId, String remarks, Character status);
}
