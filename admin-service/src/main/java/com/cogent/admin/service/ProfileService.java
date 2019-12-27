package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateRequestDTO;
import com.cogent.admin.dto.response.profile.ProfileDetailResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileDropdownDTO;
import com.cogent.admin.dto.response.profile.ProfileMinimalResponseDTO;
import com.cogent.persistence.model.Profile;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 7/2/19
 */
public interface ProfileService {

    void save(ProfileRequestDTO requestDTO);

    void update(ProfileUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<ProfileMinimalResponseDTO> search(ProfileSearchRequestDTO searchRequestDTO, Pageable pageable);

    ProfileDetailResponseDTO fetchDetailsById(Long id);

    List<ProfileDropdownDTO> fetchActiveProfilesForDropdown();

    Profile fetchActiveProfileById(Long id);

    List<ProfileDropdownDTO> fetchProfileBySubDepartmentId(Long subDepartmentId);
}
