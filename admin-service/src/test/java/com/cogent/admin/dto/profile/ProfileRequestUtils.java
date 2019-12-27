package com.cogent.admin.dto.profile;

import com.cogent.admin.dto.request.profile.*;
import com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.ProfileMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 7/10/19
 */
public class ProfileRequestUtils {

    public static ProfileRequestDTO getInvalidInputForSave() {
        return ProfileRequestDTO.builder()
                .profileDTO(getInvalidProfileDTO())
                .profileMenuRequestDTO(new ArrayList<>())
                .build();
    }

    private static ProfileDTO getInvalidProfileDTO() {
        return ProfileDTO.builder()
                .name("")
                .status('M')
                .build();
    }

    public static ProfileUpdateRequestDTO getInvalidInputForUpdate() {
        return ProfileUpdateRequestDTO.builder()
                .profileDTO(getInvalidUpdatedProfileDTO())
                .profileMenuRequestDTO(new ArrayList<>())
                .build();
    }

    private static ProfileUpdateDTO getInvalidUpdatedProfileDTO() {
        return ProfileUpdateDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    /*FOR SAVE*/
    public static ProfileRequestDTO getProfileRequestDTOThatThrowsException() {
        return new ProfileRequestDTO(new ProfileDTO(
                "admin", "This is super admin profile", 'Y', 1L),
                new ArrayList<>());
    }

    public static ProfileRequestDTO getProfileRequestDTO() {
        return new ProfileRequestDTO(getProfileDTO(), getProfileMenuRequestDTO());
    }

    public static ProfileDTO getProfileDTO() {
        return new ProfileDTO(
                "Super Admin", "This is super admin profile", 'Y', 1L);
    }

    private static List<ProfileMenuRequestDTO> getProfileMenuRequestDTO() {
        return Arrays.asList(new ProfileMenuRequestDTO(1L, 1L, 10L, 'Y'),
                (new ProfileMenuRequestDTO(1L, 2L, 11L, 'Y')));
    }

    public static Profile getProfileInfo() {
        return new Profile(1L, "Super Admin", "This is super admin profile", 'Y',
                SubDepartmentTestUtlis.getSubDepartmentInfo(), null);
    }

    public static List<ProfileMenu> getProfileMenu() {
        return Arrays.asList(new ProfileMenu(1L, getProfileInfo(), 1L, 1L, 10L, 'Y'),
                (new ProfileMenu(2L, getProfileInfo(), 1L, 2L, 11L, 'Y')));
    }

    /*FOR UPDATE*/
    public static ProfileUpdateRequestDTO getProfileRequestDTOForUpdate() {
        return new ProfileUpdateRequestDTO(getProfileDTOForUpdate(), getUpdatedProfileMenuRequests());
    }

    private static ProfileUpdateDTO getProfileDTOForUpdate() {
        return new ProfileUpdateDTO(1L, "Updated name", "updated description",
                'Y', 1L, "update my profile");
    }

    private static List<ProfileMenuUpdateRequestDTO> getUpdatedProfileMenuRequests() {
        return Arrays.asList(new ProfileMenuUpdateRequestDTO(1L, 1L,1L, 10L, 'N'),
                (new ProfileMenuUpdateRequestDTO(2L, 2L,1L, 11L, 'Y')),
                new ProfileMenuUpdateRequestDTO(null, 3L, 1L,10L, 'Y'));
    }

    public static Profile getUpdatedProfileInfo() {
        return new Profile(1L, "Updated name", "updated description", 'Y',
                SubDepartmentTestUtlis.getSubDepartmentInfo(), "update my profile");
    }

    public static List<ProfileMenu> getUpdatedProfileMenus() {
        return Arrays.asList(new ProfileMenu(1L, getProfileInfo(), 1L,
                        1L, 10L, 'N'),
                new ProfileMenu(2L, getProfileInfo(), 1L, 2L, 11L, 'Y'),
                new ProfileMenu(3L, getProfileInfo(), 2L, 3L, 10L, 'Y'));
    }

    /*FOR DELETE*/
    public static Profile getDeletedProfileInfo() {
        return new Profile(1L, "Updated name", "updated description", 'D',
                SubDepartmentTestUtlis.getSubDepartmentInfo(),
                "Yes.. Delete it");
    }

    /*FOR SEARCH*/
    public static ProfileSearchRequestDTO getProfileSearchRequestDTO() {
        return new ProfileSearchRequestDTO("Super Admin", 'Y', 1L);
    }
}
