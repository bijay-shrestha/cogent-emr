package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.admin.AdminInfoRequestDTO;
import com.cogent.admin.dto.request.admin.AdminSearchRequestDTO;
import com.cogent.admin.dto.request.admin.AdminUpdateRequestDTO;
import com.cogent.admin.dto.response.admin.AdminDetailResponseDTO;
import com.cogent.admin.dto.response.admin.AdminInfoByUsernameResponseDTO;
import com.cogent.admin.dto.response.admin.AdminInfoResponseDTO;
import com.cogent.persistence.model.Admin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 7/21/19
 */
@Repository
@Qualifier("adminRepositoryCustom")
public interface AdminRepositoryCustom {

    List fetchAdminForValidation(String username, String email, String mobileNumber);

    List fetchActiveAdminsForDropDown();

    List search(AdminSearchRequestDTO searchRequestDTO, Pageable pageable);

    AdminDetailResponseDTO fetchDetailsById(Long id);

    List fetchAdmin(AdminUpdateRequestDTO updateRequestDTO);

    Admin fetchAdminByUsernameOrEmail(String username);

    AdminInfoResponseDTO fetchLoggedInAdminInfo(AdminInfoRequestDTO requestDTO);

   AdminInfoByUsernameResponseDTO fetchAdminInfoByUsername(String username);
}

