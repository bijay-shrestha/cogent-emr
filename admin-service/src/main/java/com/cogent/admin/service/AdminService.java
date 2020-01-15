package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.dto.response.admin.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author smriti on 2019-08-05
 */
public interface AdminService {

    void save(AdminRequestDTO adminRequestDTO, MultipartFile files);

    List<AdminDropdownDTO> fetchActiveAdminsForDropdown();

    List<AdminMinimalResponseDTO> search(AdminSearchRequestDTO searchRequestDTO, Pageable pageable);

    AdminDetailResponseDTO fetchDetailsById(Long id);

    void delete(DeleteRequestDTO deleteRequestDTO);

    void changePassword(UpdatePasswordRequestDTO requestDTO);

    void updateAvatar(MultipartFile files, Long adminId);

    void update(AdminUpdateRequestDTO updateRequestDTO, MultipartFile files);

    void verifyConfirmationToken(String token);

    void savePassword(AdminPasswordRequestDTO requestDTO);

    AdminLoggedInInfoResponseDTO fetchLoggedInAdminInfo(AdminInfoRequestDTO requestDTO);

    AdminInfoByUsernameResponseDTO fetchAdminInfoByUsername(String username);

    List<AdminSubDepartmentResponseDTO> fetchLoggedInAdminSubDepartmentList(String username);

    List<AdminMetaInfoResponseDTO> fetchAdminMetaInfoResponseDto();
}




