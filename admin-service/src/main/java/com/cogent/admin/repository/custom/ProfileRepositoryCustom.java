package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.response.profile.ProfileDetailResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileDropdownDTO;
import com.cogent.admin.dto.response.profile.ProfileMinimalResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 7/10/19
 */
@Repository
@Qualifier("profileRepositoryCustom")
public interface ProfileRepositoryCustom {

    Long findProfileCountByName(String name);

    Long findProfileCountByIdAndName(Long id, String name);

    List<ProfileMinimalResponseDTO> search(ProfileSearchRequestDTO searchRequestDTO, Pageable pageable);

    ProfileDetailResponseDTO fetchDetailsById(Long id);

    List<ProfileDropdownDTO> fetchActiveProfilesForDropDown();

    List<ProfileDropdownDTO> fetchProfileBySubDepartmentId(Long subDepartmentId);
}
