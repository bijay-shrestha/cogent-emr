package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.dto.response.admin.*;
import com.cogent.admin.dto.response.files.FileUploadResponseDTO;
import com.cogent.admin.feign.dto.request.email.EmailRequestDTO;
import com.cogent.persistence.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.cogent.admin.constants.EmailConstants.SUBJECT_FOR_ADMIN_VERIFICATION;
import static com.cogent.admin.constants.EmailConstants.SUBJECT_FOR_UPDATE_ADMIN;
import static com.cogent.admin.constants.EmailTemplates.ADMIN_VERIFICATION;
import static com.cogent.admin.constants.EmailTemplates.UPDATE_ADMIN;
import static com.cogent.admin.constants.StatusConstants.*;
import static com.cogent.admin.constants.StringConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.AdminConstants.BASE_ADMIN;
import static com.cogent.admin.constants.WebResourceKeyConstants.ForgotPasswordConstants.BASE_FORGOT_PASSWORD;
import static com.cogent.admin.constants.WebResourceKeyConstants.ForgotPasswordConstants.VERIFY;
import static com.cogent.admin.utils.RandomGenerator.generateRandomToken;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-08-11
 */
public class AdminUtils {

    public static Admin convertAdminRequestDTOToAdmin(AdminRequestDTO adminRequestDTO,
                                                      AdminCategory adminCategory) {
        Admin admin = new Admin();
        admin.setUsername(adminRequestDTO.getUsername());
        admin.setFullName(toUpperCase(adminRequestDTO.getFullName()));
        admin.setEmail(adminRequestDTO.getEmail());
        admin.setMobileNumber(adminRequestDTO.getMobileNumber());
        admin.setStatus(adminRequestDTO.getStatus());
        admin.setHasMacBinding(adminRequestDTO.getHasMacBinding());
        admin.setAdminCategory(adminCategory);
        admin.setIsFirstLogin(YES);
        return admin;
    }

    public static void convertAdminUpdateRequestDTOToAdmin(Admin admin,
                                                           AdminUpdateRequestDTO adminRequestDTO,
                                                           AdminCategory adminCategory) {

        admin.setFullName(toUpperCase(adminRequestDTO.getFullName()));
        admin.setEmail(adminRequestDTO.getEmail());
        admin.setMobileNumber(adminRequestDTO.getMobileNumber());
        admin.setStatus(adminRequestDTO.getStatus());
        admin.setHasMacBinding(adminRequestDTO.getHasMacBinding());
        admin.setAdminCategory(adminCategory);
        admin.setRemarks(adminRequestDTO.getRemarks());
        /*MODIFIED DATE AND MODIFIED BY*/
    }

    public static AdminApplicationModule parseToAdminApplicationModule(Long adminId, Long applicationModuleId) {
        AdminApplicationModule adminApplicationModule = new AdminApplicationModule();
        adminApplicationModule.setAdminId(adminId);
        adminApplicationModule.setApplicationModuleId(applicationModuleId);
        adminApplicationModule.setStatus(ACTIVE);
        return adminApplicationModule;
    }

    public static AdminProfile parseToAdminProfile(Long adminId, Long profileId) {
        AdminProfile adminProfile = new AdminProfile();
        adminProfile.setAdminId(adminId);
        adminProfile.setProfileId(profileId);
        adminProfile.setStatus(ACTIVE);
        return adminProfile;
    }

    public static AdminProfile parseToUpdatedAdminProfile(Long adminId,
                                                          AdminProfileUpdateRequestDTO updateDTO) {

        AdminProfile adminProfile = new AdminProfile();
        adminProfile.setId(updateDTO.getAdminProfileId());
        adminProfile.setAdminId(adminId);
        adminProfile.setProfileId(updateDTO.getProfileId());
        adminProfile.setStatus(updateDTO.getStatus());

        return adminProfile;
    }

    public static AdminApplicationModule parseToUpdatedAdminApplicationModule(
            Long adminId,
            AdminApplicationModuleUpdateRequestDTO updateDTO) {

        AdminApplicationModule adminApplicationModule = new AdminApplicationModule();
        adminApplicationModule.setId(updateDTO.getApplicationModuleId());
        adminApplicationModule.setAdminId(adminId);
        adminApplicationModule.setApplicationModuleId(updateDTO.getApplicationModuleId());
        adminApplicationModule.setStatus(updateDTO.getStatus());
        return adminApplicationModule;
    }

    public static MacAddressInfo convertToMACAddressInfo(String macAddress, Admin admin) {

        MacAddressInfo macAddressInfo = new MacAddressInfo();
        macAddressInfo.setMacAddress(macAddress);
        macAddressInfo.setStatus(ACTIVE);
        macAddressInfo.setAdmin(admin);
        return macAddressInfo;
    }

    public static AdminMetaInfo parseInAdminMetaInfo(Admin admin, AdminMetaInfo adminMetaInfo) {
        adminMetaInfo.setAdmin(admin);
        adminMetaInfo.setMetaInfo(admin.getFullName() + OR + admin.getUsername() + OR + admin.getMobileNumber());
        return adminMetaInfo;
    }

    public static AdminConfirmationToken parseInAdminConfirmationToken(Admin admin) {
        AdminConfirmationToken confirmationToken = new AdminConfirmationToken();
        confirmationToken.setAdmin(admin);
        confirmationToken.setStatus(ACTIVE);
        confirmationToken.setConfirmationToken(generateRandomToken());

        return confirmationToken;
    }

    public static EmailRequestDTO convertAdminRequestToEmailRequestDTO(AdminRequestDTO adminRequestDTO,
                                                                       String confirmationToken) {

        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String confirmationUrl = fileUri + API_V1 + BASE_ADMIN + BASE_FORGOT_PASSWORD + VERIFY
                + "?token =" + confirmationToken;

        return EmailRequestDTO.builder()
                .receiverEmailAddress(adminRequestDTO.getEmail())
                .subject(SUBJECT_FOR_ADMIN_VERIFICATION)
                .templateName(ADMIN_VERIFICATION)
                .paramValue(adminRequestDTO.getUsername() + COMMA_SEPARATED + confirmationUrl)
                .build();
    }

    public static AdminAvatar convertFileToAdminAvatar(FileUploadResponseDTO fileUploadResponseDTO,
                                                       Admin admin) {
        AdminAvatar adminAvatar = new AdminAvatar();
        setFileProperties(fileUploadResponseDTO, adminAvatar);
        adminAvatar.setAdmin(admin);
        return adminAvatar;
    }

    public static void setFileProperties(FileUploadResponseDTO fileUploadResponseDTO,
                                         AdminAvatar adminAvatar) {
        adminAvatar.setFileSize(fileUploadResponseDTO.getFileSize());
        adminAvatar.setFileUri(fileUploadResponseDTO.getFileUri());
        adminAvatar.setFileType(fileUploadResponseDTO.getFileType());
        adminAvatar.setIsDefaultImage(NO);
    }

    public static AdminDetailResponseDTO parseToAdminDetailResponseDTO(AdminResponseDTO adminResponseDTO,
                                                                       List<MacAddressInfoResponseDTO> macAddresses) {
        return AdminDetailResponseDTO.builder()
                .adminResponseDTO(adminResponseDTO)
                .macAddressInfoResponseDTOS(macAddresses)
                .build();
    }

    public static Admin convertAdminToDeleted(Admin admin, DeleteRequestDTO deleteRequestDTO) {
        admin.setStatus(deleteRequestDTO.getStatus());
        admin.setRemarks(deleteRequestDTO.getRemarks());

        return admin;
    }

    public static Admin updateAdminPassword(UpdatePasswordRequestDTO requestDTO, Admin admin) {
        admin.setPassword(BCrypt.hashpw(requestDTO.getNewPassword(), BCrypt.gensalt()));
        admin.setRemarks(requestDTO.getRemarks());
        return admin;
    }

    private static BiFunction<MacAddressInfoUpdateRequestDTO, Admin, MacAddressInfo> convertToUpdatedMACAddressInfo =
            (requestDTO, admin) -> {

                MacAddressInfo macAddressInfo = new MacAddressInfo();
                macAddressInfo.setId(requestDTO.getId());
                macAddressInfo.setMacAddress(requestDTO.getMacAddress());
                macAddressInfo.setStatus(requestDTO.getStatus());
                macAddressInfo.setAdmin(admin);

                return macAddressInfo;
            };

    public static List<MacAddressInfo> convertToUpdatedMACAddressInfo(
            List<MacAddressInfoUpdateRequestDTO> macAddressInfoRequestDTOS, Admin admin) {

        return macAddressInfoRequestDTOS.stream()
                .map(macAddressInfo -> convertToUpdatedMACAddressInfo.apply(macAddressInfo, admin))
                .collect(Collectors.toList());
    }

    public static String parseUpdatedValues(Admin admin,
                                            AdminUpdateRequestDTO updateRequestDTO) {
        Map<String, String> params = new HashMap<>();

        if (!(updateRequestDTO.getFullName().equalsIgnoreCase(admin.getFullName())))
            params.put("Full Name ", updateRequestDTO.getFullName());

        if (!(updateRequestDTO.getEmail().equalsIgnoreCase(admin.getEmail())))
            params.put("Email Address ", updateRequestDTO.getEmail());

        if (!(updateRequestDTO.getMobileNumber().equalsIgnoreCase(admin.getMobileNumber())))
            params.put("Mobile Number ", updateRequestDTO.getMobileNumber());

//        if (!(updateRequestDTO.getProfileId().equals(admin.getProfile().getId())))
//            params.put("Profile ", admin.getProfile().getName());

        if (!(updateRequestDTO.getAdminCategoryId().equals(admin.getAdminCategory().getId())))
            params.put("Admin Category ", admin.getAdminCategory().getName());

        if (!(updateRequestDTO.getStatus().equals(admin.getStatus()))) {
            String status = "";
            switch (admin.getStatus()) {
                case 'Y':
                    status = "Active";
                    break;
                case 'N':
                    status = "Inactive";
                    break;
                case 'D':
                    status = "Deleted";
                    break;
            }

            params.put("Status ", status);
        }

        params.put("Remarks ", updateRequestDTO.getRemarks());

        return params.toString();
    }

    public static String parseUpdatedMacAddress(AdminUpdateRequestDTO updateRequestDTO) {

        List<String> macAddress = updateRequestDTO.getMacAddressInfoUpdateRequestDTOS()
                .stream()
                .filter(requestDTO -> requestDTO.getStatus().equals(ACTIVE))
                .map(MacAddressInfoUpdateRequestDTO::getMacAddress)
                .collect(Collectors.toList());

        return (updateRequestDTO.getHasMacBinding().equals(ACTIVE))
                ? StringUtils.join(macAddress, COMMA_SEPARATED) : "N/A";
    }

    public static EmailRequestDTO parseToEmailRequestDTO(String username,
                                                         AdminUpdateRequestDTO updateRequestDTO,
                                                         String paramValues,
                                                         String updatedMacAddress) {
        return EmailRequestDTO.builder()
                .receiverEmailAddress(updateRequestDTO.getEmail())
                .subject(SUBJECT_FOR_UPDATE_ADMIN)
                .templateName(UPDATE_ADMIN)
                .paramValue(username + HYPHEN + paramValues + HYPHEN +
                        updateRequestDTO.getHasMacBinding() + HYPHEN + updatedMacAddress)
                .build();
    }

    public static Admin saveAdminPassword(PasswordRequestDTO requestDTO,
                                          AdminConfirmationToken confirmationToken) {
        Admin admin = confirmationToken.getAdmin();
        admin.setPassword(BCrypt.hashpw(requestDTO.getPassword(), BCrypt.gensalt()));
        return admin;
    }

    public static Function<Object[], AdminResponseDTO> parseToAdminResponse = (objects) -> {

        final int ADMIN_ID_INDEX = 0;
        final int FULL_NAME_INDEX = 1;
        final int USERNAME_INDEX = 2;
        final int EMAIL_INDEX = 3;
        final int MOBILE_NUMBER_INDEX = 4;
        final int STATUS_INDEX = 5;
        final int HAS_MAC_BINDING_INDEX = 6;
        final int ADMIN_CATEGORY_NAME_INDEX = 7;
        final int ADMIN_AVATAR_FILE_URI_INDEX = 8;
        final int ADMIN_AVATAR_IS_DEFAULT_INDEX = 9;
        final int ADMIN_CATEGORY_ID_INDEX = 10;
        final int REMARKS_INDEX = 11;
        final int ADMIN_PROFILE_ID_INDEX = 12;
        final int PROFILE_ID_INDEX = 13;
        final int ADMIN_APPLICATION_MODULE_ID_INDEX = 14;
        final int APPLICATION_MODULE_INDEX = 15;

        String[] adminProfileIds = objects[ADMIN_PROFILE_ID_INDEX].toString().split(COMMA_SEPARATED);
        String[] profileIds = objects[PROFILE_ID_INDEX].toString().split(COMMA_SEPARATED);

        String[] adminApplicationModuleIds = objects[ADMIN_APPLICATION_MODULE_ID_INDEX].toString().split(COMMA_SEPARATED);
        String[] applicationModuleIds = objects[APPLICATION_MODULE_INDEX].toString().split(COMMA_SEPARATED);

        return AdminResponseDTO.builder()
                .id(Long.parseLong(objects[ADMIN_ID_INDEX].toString()))
                .fullName(objects[FULL_NAME_INDEX].toString())
                .username(objects[USERNAME_INDEX].toString())
                .email(objects[EMAIL_INDEX].toString())
                .mobileNumber(objects[MOBILE_NUMBER_INDEX].toString())
                .status(objects[STATUS_INDEX].toString().charAt(0))
                .hasMacBinding(objects[HAS_MAC_BINDING_INDEX].toString().charAt(0))
                .adminCategoryName(objects[ADMIN_CATEGORY_NAME_INDEX].toString())
                .fileUri(objects[ADMIN_AVATAR_FILE_URI_INDEX].toString())
                .isDefaultImage(objects[ADMIN_AVATAR_IS_DEFAULT_INDEX].toString().charAt(0))
                .adminCategoryId(Long.parseLong(objects[ADMIN_CATEGORY_ID_INDEX].toString()))
                .remarks(objects[REMARKS_INDEX].toString())
                .adminProfileResponseDTOS(getAdminProfileResponseDTOS(adminProfileIds, profileIds))
                .adminApplicationModuleResponseDTOS(getAdminApplicationModuleResponseDTOS(
                        adminApplicationModuleIds, applicationModuleIds))
                .build();
    };

    private static List<AdminProfileResponseDTO> getAdminProfileResponseDTOS(String[] adminProfileIds,
                                                                             String[] profileIds) {

        return IntStream.range(0, adminProfileIds.length)
                .mapToObj(i -> AdminProfileResponseDTO.builder()
                        .adminProfileId(Long.parseLong(adminProfileIds[i]))
                        .profileId(Long.parseLong(profileIds[i]))
                        .build()).collect(Collectors.toList());
    }

    private static List<AdminApplicationModuleResponseDTO> getAdminApplicationModuleResponseDTOS(
            String[] adminApplicationModuleIds,
            String[] applicationModuleIds) {

        return IntStream.range(0, adminApplicationModuleIds.length)
                .mapToObj(i -> AdminApplicationModuleResponseDTO.builder()
                        .adminApplicationModuleId(Long.parseLong(adminApplicationModuleIds[i]))
                        .applicationModuleId(Long.parseLong(applicationModuleIds[i]))
                        .build()).collect(Collectors.toList());
    }

    public static AdminInfoByUsernameResponseDTO parseToAdminInfoByUsernameResponseDTO(Object[] queryResult) {

        final int ASSIGNED_SUB_DEPARTMENT_CODES_INDEX = 0;
        final int PASSWORD_INDEX = 1;

        List<String> subDepartmentCodes = new ArrayList<>(Arrays.asList(
                queryResult[ASSIGNED_SUB_DEPARTMENT_CODES_INDEX].toString().split(COMMA_SEPARATED)));

        return AdminInfoByUsernameResponseDTO.builder()
                .assignedApplicationModuleCodes(subDepartmentCodes)
                .password(queryResult[PASSWORD_INDEX].toString())
                .build();
    }
}
