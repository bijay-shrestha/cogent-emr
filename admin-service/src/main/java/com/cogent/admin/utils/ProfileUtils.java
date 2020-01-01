package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateDTO;
import com.cogent.admin.dto.response.profile.*;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.SubDepartment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cogent.admin.constants.StringConstant.COMMA_SEPARATED;
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

    public static AssignedProfileResponseDTO parseToAssignedProfileMenuResponseDTO(List<Object[]> results) {

        List<ChildMenusResponseDTO> childMenusResponseDTOS = parseToChildMenusResponseDTOS(results);

        List<AssignedRolesResponseDTO> assignedRolesResponseDTOS =
                parseToAssignedRolesResponseDTOS(childMenusResponseDTOS);

        return parseToAssignedProfileMenuResponseDTO(assignedRolesResponseDTOS, results);
    }

    private static List<ChildMenusResponseDTO> parseToChildMenusResponseDTOS(List<Object[]> results) {

        final int PARENT_ID_INDEX = 0;
        final int USER_MENU_ID_INDEX = 1;
        final int ROLE_ID_INDEX = 2;

        List<ChildMenusResponseDTO> childMenusResponseDTOS = new ArrayList<>();

        results.forEach(result -> {
            List<Long> roleIds = Stream.of(result[ROLE_ID_INDEX].toString()
                    .split(COMMA_SEPARATED))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            ChildMenusResponseDTO responseDTO = ChildMenusResponseDTO.builder()
                    .parentId(Long.parseLong(result[PARENT_ID_INDEX].toString()))
                    .userMenuId(Long.parseLong(result[USER_MENU_ID_INDEX].toString()))
                    .roleId(roleIds)
                    .build();

            childMenusResponseDTOS.add(responseDTO);
        });

        return childMenusResponseDTOS;
    }

    private static List<AssignedRolesResponseDTO> parseToAssignedRolesResponseDTOS
            (List<ChildMenusResponseDTO> childMenusResponseDTOS) {

        Map<Long, List<ChildMenusResponseDTO>> groupByParentIdMap =
                childMenusResponseDTOS.stream().collect(Collectors.groupingBy(ChildMenusResponseDTO::getParentId));

        return groupByParentIdMap.entrySet()
                .stream()
                .map(map -> new AssignedRolesResponseDTO(map.getKey(), map.getValue()))
                .collect(Collectors.toList());
    }

    private static AssignedProfileResponseDTO parseToAssignedProfileMenuResponseDTO
            (List<AssignedRolesResponseDTO> assignedRolesResponseDTOS,
             List<Object[]> queryResults) {

        /*BECAUSE SUB-DEPARTMENT DETAILS REMAIN THE SAME*/
        final int SUB_DEPARTMENT_CODE_INDEX = 3;
        final int SUB_DEPARTMENT_NAME_INDEX = 4;

        Object[] firstObject = queryResults.get(0);
        String subDepartmentName = firstObject[SUB_DEPARTMENT_NAME_INDEX].toString();
        String subDepartmentCode = firstObject[SUB_DEPARTMENT_CODE_INDEX].toString();

        return AssignedProfileResponseDTO.builder()
                .subDepartmentName(subDepartmentName)
                .subDepartmentCode(subDepartmentCode)
                .assignedRolesResponseDTOS(assignedRolesResponseDTOS)
                .build();
    }
}
