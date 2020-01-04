package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileMenuSearchRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateRequestDTO;
import com.cogent.admin.dto.response.profile.AssignedProfileResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileDetailResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileDropdownDTO;
import com.cogent.admin.dto.response.profile.ProfileMinimalResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ProfileMenuRepository;
import com.cogent.admin.repository.ProfileRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.ProfileService;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.ProfileMenu;
import com.cogent.persistence.model.SubDepartment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.ProfileServiceMessages.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.ProfileServiceMessages.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ProfileLog.PROFILE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ProfileMenuUtils.convertToProfileMenu;
import static com.cogent.admin.utils.ProfileMenuUtils.convertToUpdatedProfileMenu;
import static com.cogent.admin.utils.ProfileUtils.*;

/**
 * @author smriti on 7/2/19
 */
@Service
@Transactional
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileMenuRepository profileMenuRepository;

    private final SubDepartmentRepository subDepartmentRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              ProfileMenuRepository profileMenuRepository,
                              SubDepartmentRepository subDepartmentRepository) {
        this.profileRepository = profileRepository;
        this.profileMenuRepository = profileMenuRepository;
        this.subDepartmentRepository = subDepartmentRepository;
    }

    @Override
    public void save(ProfileRequestDTO profileRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, PROFILE);

        validateName(profileRepository.findProfileCountByName(profileRequestDTO.getProfileDTO().getName()),
                profileRequestDTO.getProfileDTO().getName());

        SubDepartment subDepartment = findSubDepartmentById(profileRequestDTO.getProfileDTO().getSubDepartmentId());

        Profile savedProfile = save(convertDTOToProfile(profileRequestDTO.getProfileDTO(), subDepartment));

        saveProfileMenu(convertToProfileMenu(savedProfile, profileRequestDTO.getProfileMenuRequestDTO()));

        log.info(SAVING_PROCESS_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));
    }

    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    public void saveProfileMenu(List<ProfileMenu> profileMenus) {
        profileMenuRepository.saveAll(profileMenus);
    }

    @Override
    public void update(ProfileUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, PROFILE);

        Profile profile = findById(requestDTO.getProfileDTO().getId());

        validateName(profileRepository.findProfileCountByIdAndName(requestDTO.getProfileDTO().getId(),
                requestDTO.getProfileDTO().getName()), requestDTO.getProfileDTO().getName());

        SubDepartment subDepartment = findSubDepartmentById(requestDTO.getProfileDTO().getSubDepartmentId());

        save(convertToUpdatedProfile(requestDTO.getProfileDTO(), subDepartment, profile));

        saveProfileMenu(convertToUpdatedProfileMenu(profile, requestDTO.getProfileMenuRequestDTO()));

        log.info(UPDATING_PROCESS_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));
    }

    public Profile findById(Long profileId) {
        return profileRepository.findProfileById(profileId)
                .orElseThrow(() -> PROFILE_WITH_GIVEN_ID_NOT_FOUND.apply(profileId));
    }

    private void validateName(Long profileCount, String name) {
        if (profileCount.intValue() > 0)
            throw new DataDuplicationException(
                    Profile.class, NAME_DUPLICATION_MESSAGE + name, NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, PROFILE);

        Profile profile = findById(deleteRequestDTO.getId());

        save(convertProfileToDeleted.apply(profile, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<ProfileMinimalResponseDTO> search(ProfileSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, PROFILE);

        List<ProfileMinimalResponseDTO> responseDTOS = profileRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public ProfileDetailResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, PROFILE);

        ProfileDetailResponseDTO detailResponseDTO = profileRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));

        return detailResponseDTO;
    }

    @Override
    public List<ProfileDropdownDTO> fetchActiveProfilesForDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, PROFILE);

        List<ProfileDropdownDTO> responseDTOS = profileRepository.fetchActiveProfilesForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Profile fetchActiveProfileById(Long id) {
        return profileRepository.findActiveProfileById(id)
                .orElseThrow(() -> PROFILE_WITH_GIVEN_ID_NOT_FOUND.apply(id));
    }

    @Override
    public List<ProfileDropdownDTO> fetchProfileBySubDepartmentId(Long subDepartmentId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, PROFILE);

        List<ProfileDropdownDTO> responseDTOS = profileRepository.fetchProfileBySubDepartmentId(subDepartmentId);

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public AssignedProfileResponseDTO fetchAssignedProfileResponseDto(ProfileMenuSearchRequestDTO searchRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, PROFILE);

        AssignedProfileResponseDTO responseDTO =
                profileRepository.fetchAssignedProfileResponseDto(searchRequestDTO);

        log.info(FETCHING_PROCESS_COMPLETED, PROFILE, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    private Function<Long, NoContentFoundException> PROFILE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Profile.class, "id", id.toString());
    };

    public SubDepartment findSubDepartmentById(Long id) {
        return subDepartmentRepository.findActiveSubDepartmentById(id)
                .orElseThrow(() -> new NoContentFoundException(SubDepartment.class, "id", id.toString()));
    }

}

