package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateDTO;
import com.cogent.admin.dto.response.profile.ProfileDetailResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileMenuResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileResponseDTO;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.SubDepartment;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 7/8/19
 */
public class ProfileUtils {

    public static Profile convertDTOToProfile(ProfileDTO profileDTO,
                                              SubDepartment subDepartment) {
        Profile profile = new Profile();
        profile.setName(toUpperCase(profileDTO.getName()));
        profile.setDescription(profileDTO.getDescription());
        profile.setStatus(profileDTO.getStatus());
        profile.setSubDepartment(subDepartment);
        return profile;
    }

    public static Profile convertToUpdatedProfile(ProfileUpdateDTO profileDTO,
                                                  SubDepartment subDepartment,
                                                  Profile profile) {
        profile.setName(toUpperCase(profileDTO.getName()));
        profile.setDescription(profileDTO.getDescription());
        profile.setStatus(profileDTO.getStatus());
        profile.setSubDepartment(subDepartment);
        profile.setRemarks(profileDTO.getRemarks());
        return profile;
    }

    public static ProfileDetailResponseDTO parseToProfileDetailResponseDTO(
            ProfileResponseDTO profileResponseDTO,
            List<ProfileMenuResponseDTO> profileMenuResponseDTOS) {

        Map<Long, List<ProfileMenuResponseDTO>> groupByParentId = new TreeMap<>(
                profileMenuResponseDTOS.stream()
                        .collect(Collectors.groupingBy(ProfileMenuResponseDTO::getParentId)));

        return ProfileDetailResponseDTO.builder()
                .profileResponseDTO(profileResponseDTO)
                .profileMenuResponseDTOS(groupByParentId)
                .build();
    }

    public static BiFunction<Profile, DeleteRequestDTO, Profile> convertProfileToDeleted =
            (profile, deleteRequestDTO) -> {
                profile.setStatus(deleteRequestDTO.getStatus());
                profile.setRemarks(deleteRequestDTO.getRemarks());
                return profile;
            };
}
