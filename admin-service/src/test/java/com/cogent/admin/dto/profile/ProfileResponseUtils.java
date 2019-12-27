package com.cogent.admin.dto.profile;

import com.cogent.admin.dto.response.profile.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author smriti on 7/12/19
 */

public class ProfileResponseUtils {

    public static List<ProfileMinimalResponseDTO> getProfileMinimalResponseList() {
        return Arrays.asList(
                new ProfileMinimalResponseDTO(1L, "F1SOFT", 'Y',
                        "MEDICAL", 100),
                new ProfileMinimalResponseDTO(2L, "COGENT", 'Y',
                        "PHARMACY", 100));
    }

    public static ProfileDetailResponseDTO getProfileDetailResponse() {
        return new ProfileDetailResponseDTO(getProfileResponse(), getProfileMenuResponse());
    }

    public static ProfileResponseDTO getProfileResponse() {
        return new ProfileResponseDTO("f1soft profile", "This is f1soft profile", 'Y',
                1L, "Sub-dept 1", "SD1",
                1L, "Depart-1", null);
    }

    private static Map<Long, List<ProfileMenuResponseDTO>> getProfileMenuResponse() {
        List<ProfileMenuResponseDTO> profileMenus = Arrays.asList(
                new ProfileMenuResponseDTO(1L, 1L, 1L, 1L, 'Y'),
                new ProfileMenuResponseDTO(2L, 2L, 1L, 2L, 'Y'));

        return new TreeMap<>(profileMenus.stream()
                .collect(Collectors.groupingBy(ProfileMenuResponseDTO::getUserMenuId)));
    }

    public static List<ProfileDropdownDTO> getProfilesForDropdown() {

        return Arrays.asList(
                ProfileDropdownDTO.builder()
                        .value(1L)
                        .label("F1SOFT")
                        .build(),
                ProfileDropdownDTO.builder()
                        .value(1L)
                        .label("COGENT")
                        .build()
        );
    }
}
