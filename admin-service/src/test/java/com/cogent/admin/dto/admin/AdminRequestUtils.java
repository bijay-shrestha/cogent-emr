package com.cogent.admin.dto.admin;

import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.utils.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author smriti on 2019-08-05
 */
public class AdminRequestUtils {

    public static AdminRequestDTO getAdminRequestDTO() {
        return AdminRequestDTO.builder()
                .fullName("Cogent Health")
                .username("cogent")
                .email("cogent.health@outlook.com")
                .mobileNumber("9841111112")
                .adminCategoryId(1L)
                .status('Y')
                .hasMacBinding('Y')
                .macAddressInfoRequestDTOS(asList("fe80::48c6:851e:3a4a:874d",
                        "fe80::48c6:851e:3a4a:874a"))
                .adminProfileRequestDTO(getAdminProfileRequestDTO())
                .build();
    }

    private static List<AdminProfileRequestDTO> getAdminProfileRequestDTO(){
        return (List<AdminProfileRequestDTO>) AdminProfileRequestDTO.builder()
                .profileId(1L)
                .applicationModuleId(1L)
                .build();
    }

    public static AdminRequestDTO getAdminRequestDTOWithInValidInput() {
        return AdminRequestDTO.builder()
                .email("cogenthealthoutlookcom")
                .status('M')
                .build();
    }

    public static AdminUpdateRequestDTO getAdminUpdateRequestDTOWithInValidInput() {
        return AdminUpdateRequestDTO.builder()
                .id(1L)
                .email("cogenthealthoutlookcom")
                .status('M')
                .build();
    }

    public static AdminRequestDTO getAdminRequestDTOWithEmptyMACAddressInfo() {
        return AdminRequestDTO.builder()
                .fullName("Cogent Health")
                .username("cogent")
                .email("cogent.health@outlook.com")
                .mobileNumber("9841111112")
                .adminCategoryId(1L)
                .status('Y')
                .hasMacBinding('Y')
                .macAddressInfoRequestDTOS(new ArrayList<>())
                .build();
    }

    public static AdminSearchRequestDTO getAdminSearchRequestDTO() {
        return AdminSearchRequestDTO.builder()
                .adminMetaInfoId(1L)
                .status('Y')
                .adminCategoryId(1L)
                .profileId(1L)
                .build();
    }

    public static UpdatePasswordRequestDTO getAdminPasswordRequestDTO() {
        return UpdatePasswordRequestDTO.builder()
                .id(1L)
                .newPassword("admin1")
                .oldPassword("cogent")
                .remarks("change password")
                .build();
    }

    public static AdminUpdateRequestDTO getAdminUpdateRequestDTO() {
        return AdminUpdateRequestDTO.builder()
                .id(3L)
                .fullName("Cogent Health updated")
                .email("cogent.health@outlook.com")
                .mobileNumber("9841111112")
                .adminCategoryId(1L)
                .status('Y')
                .hasMacBinding('Y')
                .adminProfileUpdateRequestDTOS(getAdminProfileUpdateRequestDTOS())
                .macAddressInfoUpdateRequestDTOS(getAdminMacAddressUpdateRequestDTO())
                .build();
    }

    private static List<MacAddressInfoUpdateRequestDTO> getAdminMacAddressUpdateRequestDTO() {
        return asList(
                MacAddressInfoUpdateRequestDTO.builder()
                        .id(1L)
                        .macAddress("cogent")
                        .status('N')
                        .build(),
                MacAddressInfoUpdateRequestDTO.builder()
                        .id(2L)
                        .macAddress("fe80::48c6:851e:3a4a:874d")
                        .status('Y')
                        .build()
        );
    }

    private static List<AdminProfileUpdateRequestDTO> getAdminProfileUpdateRequestDTOS() {
        return asList(
                AdminProfileUpdateRequestDTO.builder()
                        .adminProfileId(1L)
                        .profileId(1L)
                        .status('N')
                        .build(),
                AdminProfileUpdateRequestDTO.builder()
                        .adminProfileId(null)
                        .profileId(2L)
                        .status('Y')
                        .build()
        );
    }

    public static String getConfirmationToken() {
        return RandomNumberGenerator.generateRandomToken();
    }

    public static AdminPasswordRequestDTO getPasswordRequestDTO() {
        return AdminPasswordRequestDTO.builder()
                .token(getConfirmationToken())
                .password("admin")
                .build();
    }
}
