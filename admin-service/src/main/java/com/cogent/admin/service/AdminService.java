package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.dto.response.admin.AdminDetailResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author smriti on 2019-08-05
 */
public interface AdminService {

    void save(AdminRequestDTO adminRequestDTO, MultipartFile files);

    List fetchActiveAdminsForDropdown();

    List search(AdminSearchRequestDTO searchRequestDTO, Pageable pageable);

    AdminDetailResponseDTO fetchDetailsById(Long id);

    void delete(DeleteRequestDTO deleteRequestDTO);

    void changePassword(UpdatePasswordRequestDTO requestDTO);

    void updateAvatar(MultipartFile files, Long adminId);

    void update(AdminUpdateRequestDTO updateRequestDTO, MultipartFile files);

    void verifyConfirmationToken(String token);

    void savePassword(PasswordRequestDTO requestDTO);
}




