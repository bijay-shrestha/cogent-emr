package com.cogent.admin.dto.admin;

import com.cogent.admin.dto.response.admin.AdminDetailResponseDTO;
import com.cogent.admin.dto.response.admin.AdminDropdownDTO;
import com.cogent.admin.dto.response.admin.AdminMinimalResponseDTO;
import com.cogent.admin.dto.response.admin.MacAddressInfoResponseDTO;
import com.cogent.admin.dto.response.files.FileUploadResponseDTO;
import com.cogent.persistence.model.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.constants.StatusConstants.ACTIVE;
import static com.cogent.admin.dto.adminCategory.AdminCategoryResponseUtils.getAdminCategory;
import static com.cogent.admin.utils.RandomNumberGenerator.generateRandomToken;

/**
 * @author smriti on 2019-08-07
 */
public class AdminResponseUtils {

    public static Admin getAdmin() {
        Admin admin = new Admin();

        admin.setId(1L);
        admin.setFullName("COGENT HEALTH");
        admin.setUsername("cogent");
        admin.setEmail("cogent.health@outlook.com");
        admin.setPassword(BCrypt.hashpw("cogent", BCrypt.gensalt()));
        admin.setMobileNumber("9841111112");
        admin.setStatus('Y');
        admin.setIsFirstLogin('Y');
        admin.setHasMacBinding('Y');
        admin.setAdminCategory(getAdminCategory());
        admin.setRemarks(null);

        return admin;
    }

    public static List<MacAddressInfo> getAdminMacAddress() {

        return Arrays.asList(new MacAddressInfo(1L, "fe80::48c6:851e:3a4a:874c", 'Y', getAdmin()),
                new MacAddressInfo(2L, "fe80::48c6:851e:3a4a:874d", 'Y', getAdmin()));
    }

    public static AdminMetaInfo getAdminMetaInfo() {
        AdminMetaInfo adminMetaInfo = new AdminMetaInfo();
        adminMetaInfo.setAdmin(getAdmin());
        adminMetaInfo.setMetaInfo("COGENT HEALTH|cogent|9841111112");
        return adminMetaInfo;
    }

    public static AdminConfirmationToken getAdminConfirmationToken() {
        AdminConfirmationToken confirmationToken = new AdminConfirmationToken();
        confirmationToken.setAdmin(getAdmin());
        confirmationToken.setStatus(ACTIVE);
        confirmationToken.setConfirmationToken(generateRandomToken());

        return confirmationToken;
    }

    public static List<Object[]> getAdminObjectWithDuplicateUsername() {
        List<Object[]> objects = new ArrayList<>();

        Object[] object = new Object[3];
        object[0] = "cogent";
        object[1] = "cogent.health@outlook.com";
        object[2] = "9841111112";
        objects.add(object);
        return objects;
    }

    public static List<FileUploadResponseDTO> getFileUploadResponse() {
        return Arrays.asList(FileUploadResponseDTO.builder()
                .fileUri("http://localhost:8081/files/Admin-cogent/test.png")
                .fileType("image/png")
                .fileSize(100L)
                .build());
    }

    public static List<FileUploadResponseDTO> getUpdatedFileUploadResponse() {
        return Arrays.asList(FileUploadResponseDTO.builder()
                .fileUri("http://localhost:8081/files/Admin-cogent/testUpdate.png")
                .fileType("image/png")
                .fileSize(100L)
                .build());
    }

    public static AdminAvatar getAdminAvatar() {
        AdminAvatar adminAvatar = new AdminAvatar();
        adminAvatar.setAdmin(getAdmin());
        adminAvatar.setFileType("image/png");
        adminAvatar.setFileUri("http://localhost:8081/files/Admin-cogent/test.png");
        adminAvatar.setFileSize(100L);
        adminAvatar.setStatus('Y');
        return adminAvatar;
    }

    public static AdminAvatar getUpdatedAdminAvatar() {
        AdminAvatar adminAvatar = new AdminAvatar();
        adminAvatar.setAdmin(getAdmin());
        adminAvatar.setFileType("image/png");
        adminAvatar.setFileUri("http://localhost:8081/files/Admin-cogent/testUpdate.png");
        adminAvatar.setFileSize(100L);
        adminAvatar.setStatus('Y');
        return adminAvatar;
    }

    public static List getAdminForDropdown() {
        return Arrays.asList(
                AdminDropdownDTO.builder()
                        .value(1L)
                        .label("SuperAdmin")
                        .build(),
                AdminDropdownDTO.builder()
                        .value(1L)
                        .label("Limited Admin")
                        .build()
        );
    }

    public static List getAdminMinimalResponseDTO() {
        return Arrays.asList(
                AdminMinimalResponseDTO.builder()
                        .id(BigInteger.valueOf(1L))
                        .fullName("COGENT HEALTH")
                        .username("cogent")
                        .email("cogent.health@outlook.com")
                        .mobileNumber("9841111112")
                        .status('Y')
                        .hasMacBinding('Y')
                        .profileName("Super Admin")
                        .adminCategoryName("Doctor")
                        .fileUri("http://localhost:8081/files/Admin-cogent/test.png")
                        .build());
    }

    public static AdminDetailResponseDTO getAdminDetailResponseDTO() {
        return AdminDetailResponseDTO.builder()
                .id(1L)
                .fullName("COGENT HEALTH")
                .username("cogent")
                .email("cogent.health@outlook.com")
                .mobileNumber("9841111112")
                .status('Y')
                .hasMacBinding('Y')
                .adminCategoryName("Doctor")
                .fileUri("http://localhost:8081/files/Admin-cogent/test.png")
                .remarks(null)
                .hasMacBinding('Y')
                .macAddressInfoResponseDTOS(getMacAddressResponseDTO())
                .build();
    }

    private static List getMacAddressResponseDTO() {
        return Arrays.asList(
                MacAddressInfoResponseDTO.builder()
                        .id(1L)
                        .macAddress("fe80::48c6:851e:3a4a:874c")
                        .build(),
                MacAddressInfoResponseDTO.builder()
                        .id(1L)
                        .macAddress("fe80::48c6:851e:3a4a:874d")
                        .build());
    }

    public static Admin getDeletedAdminInfo() {
        Admin admin = getAdmin();
        admin.setStatus('D');
        admin.setRemarks("delete it");
        return admin;
    }

    public static List<Object[]> getAdminObjectWithDuplicateEmailAndMobileNumber() {
        List<Object[]> objects = new ArrayList<>();

        Object[] object = new Object[3];
        object[0] = "cogent.health@outlook.com";
        object[1] = "9841111112";
        objects.add(object);
        return objects;
    }

}

