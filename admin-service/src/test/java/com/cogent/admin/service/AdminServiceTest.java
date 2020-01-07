package com.cogent.admin.service;

import com.cogent.admin.constants.StatusConstants;
import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.dto.response.admin.AdminDetailResponseDTO;
import com.cogent.admin.exception.*;
import com.cogent.admin.feign.service.EmailService;
import com.cogent.admin.repository.*;
import com.cogent.admin.service.impl.AdminServiceImpl;
import com.cogent.admin.utils.AdminUtils;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.AdminAvatar;
import com.cogent.persistence.model.AdminConfirmationToken;
import com.cogent.persistence.model.AdminMetaInfo;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.admin.AdminRequestUtils.*;
import static com.cogent.admin.dto.admin.AdminResponseUtils.*;
import static com.cogent.admin.dto.adminCategory.AdminCategoryResponseUtils.getAdminCategory;
import static com.cogent.admin.dto.files.MultipartFileUtils.getMockMultipartFile;
import static com.cogent.admin.dto.files.MultipartFileUtils.getUpdatedMultipartFile;
import static com.cogent.admin.dto.profile.ProfileResponseUtils.getProfilesForDropdown;
import static com.cogent.admin.dto.profile.ProfileRequestUtils.getProfileInfo;
import static com.cogent.admin.utils.AdminUtils.convertFileToAdminAvatar;
import static com.cogent.admin.utils.HttpServletRequestUtils.getMockHttpServletRequest;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-08-05
 */
public class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminProfileRepository adminProfileRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private AdminCategoryService adminCategoryService;

    @Mock
    private MacAddressInfoRepository macAddressInfoRepository;

    @Mock
    private AdminMetaInfoRepository adminMetaInfoRepository;

    @Mock
    private AdminConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private Validator validator;

    @Mock
    private StorageService storageService;

    @Mock
    private AdminAvatarRepository adminAvatarRepository;

    @Mock
    private AdminApplicationModuleRepository adminApplicationModuleRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    public void saveAdminTest() {
        Should_Throw_Exception_When_InputIsInvalidForCreate();

        Should_Throw_Exception_When_Admin_Duplicates();

        Should_Throw_Exception_When_AdminCategory_Not_Found();

        Should_Successfully_Save_Admin();

        Should_Successfully_Save_Admin_ApplicationModule();

        Should_Throw_Exception_When_Profile_Not_Found();

        Should_Successfully_Save_AdminProfile();

        Should_Successfully_Save_AdminAvatar();

        Should_Throw_Exception_When_MacAddressInfo_Is_Empty();

        Should_Successfully_Save_MACAddressInfo();

        Should_Save_AdminMetaInfo();

        Should_Save_AdminConfirmationToken();
    }

    public void searchTest() {
        Should_Throw_Exception_When_AdminIsEmpty();

        Should_Return_Admins();
    }

    public void fetchDetailsTest() {
        Should_ThrowException_When_Admin_Is_Null();
        Should_Return_Admin_Details();
    }

    public void fetchAdminForDropdownTest() {
        Should_ThrowException_When_AdminNotFound();

        Should_Return_Admin_For_Dropdown();
    }

    public void changePasswordTest() {
        Should_Throw_Exception_Admin_Is_Null();

        Should_Throw_Exception_When_Password_MisMatch();

        Should_Throw_Exception_When_Password_Duplicates();
    }

    public void changeAvatarTest() {
        Should_Throw_Exception_When_Admin_Is_Null();

        Should__Save_New_AdminAvatar();

        Should_Update_Admin_Avatar();
    }

    public void updateAdmin() {
        Should_Throw_Exception_When_AdminIsNull();

        Should_Throw_Exception_When_AdminDuplicates();

        Should_Throw_Exception_When_AdminCategoryIsNot_Found();

        Should_Successfully_Update_Admin();

        Should_Update_Admin_Application_Module();

        Should_Throw_Exception_When_ProfileIsNotFound();

        Should__Save_New_Admin_Avatar();

        Should_Successfully_Update_Admin_Avatar();
    }

    @Test
    public void verifyConfirmationToken() {
        Should_Throw_Exception_When_ConfirmationToken_Is_Invalid();
        Should_Throw_Exception_When_AdminIsRegistered();
    }

    @Test
    public void savePassword() {
        savePassword_ShouldThrowException();
        Should_Successfully_Save_Password();
    }

    @Test(expected = ConstraintViolationException.class)
    public void Should_Throw_Exception_When_InputIsInvalidForCreate() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTOWithInValidInput();

        given(validator.validate(adminRequestDTO)).willThrow(ConstraintViolationException.class);
        adminService.save(adminRequestDTO, getMockMultipartFile());
    }

    @Test(expected = DataDuplicationException.class)
    public void Should_Throw_Exception_When_Admin_Duplicates() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        given(adminRepository.fetchAdminForValidation(adminRequestDTO.getUsername(), adminRequestDTO.getEmail(),
                adminRequestDTO.getMobileNumber())).willReturn(getAdminObjectWithDuplicateUsername());

        adminService.save(adminRequestDTO, getMockMultipartFile());
    }

    @Test
    public void Should_Throw_Exception_When_Profile_Not_Found() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        adminRequestDTO.getProfileIds().forEach(
                id -> given(profileService.fetchActiveProfileById(id)).willThrow(NoContentFoundException.class));

        thrown.expect(NoContentFoundException.class);

        adminService.save(adminRequestDTO, getMockMultipartFile());
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_AdminCategory_Not_Found() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        given(adminCategoryService.fetchActiveAdminCategoryById(adminRequestDTO.getAdminCategoryId()))
                .willThrow(new NoContentFoundException(Admin.class));

        adminService.save(adminRequestDTO, getMockMultipartFile());
    }

    @Test
    public void Should_Successfully_Save_Admin() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        Admin expected = AdminUtils.convertAdminRequestDTOToAdmin(adminRequestDTO,
                getAdminCategory());

        saveAdmin(adminRequestDTO);

        adminService.save(adminRequestDTO, getMockMultipartFile());

        verify(adminRepository, times(1)).save(any(Admin.class));

        Assert.assertTrue(new ReflectionEquals(expected,
                new String[]{"password", "id", "adminCategory"}).matches(getAdmin()));
    }

    @Test
    public void Should_Successfully_Save_Admin_ApplicationModule() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        saveAdmin(adminRequestDTO);

        adminService.save(adminRequestDTO, getMockMultipartFile());

        verify(adminApplicationModuleRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Save_AdminProfile() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        saveAdmin(adminRequestDTO);

        saveAdminProfile(adminRequestDTO);

        adminService.save(adminRequestDTO, getMockMultipartFile());
        verify(adminProfileRepository, times(1)).saveAll(anyIterable());
    }

    private void saveAdmin(AdminRequestDTO adminRequestDTO) {
        given(adminRepository.fetchAdminForValidation(adminRequestDTO.getUsername(),
                adminRequestDTO.getEmail(), adminRequestDTO.getMobileNumber())).willReturn(new ArrayList());

        given(adminCategoryService.fetchActiveAdminCategoryById(adminRequestDTO.getAdminCategoryId()))
                .willReturn(getAdminCategory());

        given(adminRepository.save(any(Admin.class))).willReturn(getAdmin());
    }

    private void saveAdminProfile(AdminRequestDTO adminRequestDTO) {
        adminRequestDTO.getProfileIds().forEach(
                id -> given(profileService.fetchActiveProfileById(id)).willReturn(getProfileInfo()));
    }

    @Test
    public void Should_Successfully_Save_AdminAvatar() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();
        MultipartFile files = getMockMultipartFile();

        AdminAvatar expected = convertFileToAdminAvatar(getFileUploadResponse().get(0), getAdmin());

        saveAdmin(adminRequestDTO);

        saveAdminProfile(adminRequestDTO);

        saveAdminAvatar();

        adminService.save(adminRequestDTO, files);

        verify(adminAvatarRepository, times(1)).save(any(AdminAvatar.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "admin"}).matches(getAdminAvatar()));
    }

    private void saveAdminAvatar() {
        given(storageService.uploadFiles(any(), anyString())).willReturn(getFileUploadResponse());

        given(adminAvatarRepository.save(any(AdminAvatar.class))).willReturn(getAdminAvatar());
    }

    @Test
    public void Should_Throw_Exception_When_MacAddressInfo_Is_Empty() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTOWithEmptyMACAddressInfo();

        saveAdmin(adminRequestDTO);

        saveAdminProfile(adminRequestDTO);

        saveAdminAvatar();

        thrown.expect(NoContentFoundException.class);

        adminService.save(adminRequestDTO, getMockMultipartFile());
    }

    @Test
    public void Should_Successfully_Save_MACAddressInfo() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        saveAdmin(adminRequestDTO);

        saveAdminAvatar();

        given(macAddressInfoRepository.saveAll(anyIterable())).willReturn(getAdminMacAddress());

        adminService.save(adminRequestDTO, getMockMultipartFile());

        verify(macAddressInfoRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    public void Should_Save_AdminMetaInfo() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        AdminMetaInfo expected = AdminUtils.parseInAdminMetaInfo(getAdmin(), new AdminMetaInfo());

        saveAdmin(adminRequestDTO);

        saveAdminProfile(adminRequestDTO);

        saveAdminAvatar();

        given(macAddressInfoRepository.saveAll(anyIterable())).willReturn(getAdminMacAddress());

        given(adminMetaInfoRepository.save(any(AdminMetaInfo.class))).willReturn(getAdminMetaInfo());

        adminService.save(adminRequestDTO, getMockMultipartFile());

        verify(adminMetaInfoRepository, times(1)).save(any(AdminMetaInfo.class));

        Assertions.assertThat(expected.getMetaInfo()).isEqualTo(getAdminMetaInfo().getMetaInfo());
        Assertions.assertThat(expected.getAdmin().getId()).isEqualTo(getAdminMetaInfo().getAdmin().getId());
    }

    @Test
    public void Should_Save_AdminConfirmationToken() {
        AdminRequestDTO adminRequestDTO = getAdminRequestDTO();

        saveAdmin(adminRequestDTO);

        saveAdminAvatar();

        given(macAddressInfoRepository.saveAll(anyIterable())).willReturn(getAdminMacAddress());

        given(adminMetaInfoRepository.save(any(AdminMetaInfo.class)))
                .willReturn(getAdminMetaInfo());

        given(confirmationTokenRepository.save(any(AdminConfirmationToken.class)))
                .willReturn(getAdminConfirmationToken());

        getMockHttpServletRequest();

        adminService.save(adminRequestDTO, getMockMultipartFile());

        verify(confirmationTokenRepository, times(1))
                .save(any(AdminConfirmationToken.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_ThrowException_When_AdminNotFound() {

        given(adminRepository.fetchActiveAdminsForDropDown())
                .willThrow(new NoContentFoundException(Admin.class));

        adminService.fetchActiveAdminsForDropdown();
    }

    @Test
    public void Should_Return_Admin_For_Dropdown() {
        given(adminRepository.fetchActiveAdminsForDropDown()).willReturn(getAdminForDropdown());

        adminService.fetchActiveAdminsForDropdown();

        assertThat(adminService.fetchActiveAdminsForDropdown(), samePropertyValuesAs(getProfilesForDropdown()));
    }

    @Test
    public void Should_Throw_Exception_When_AdminIsEmpty() {
        AdminSearchRequestDTO searchRequestDTO = getAdminSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(adminRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        adminService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Return_Admins() {
        AdminSearchRequestDTO searchRequestDTO = getAdminSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(adminRepository.search(searchRequestDTO, pageable))
                .willReturn(getAdminMinimalResponseDTO());

        assertThat(adminService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(getAdminMinimalResponseDTO()));

        Assertions.assertThat(adminService.search(searchRequestDTO, pageable).size())
                .isEqualTo(getAdminMinimalResponseDTO().size());
    }

    @Test
    public void Should_ThrowException_When_Admin_Is_Null() {
        Long id = 1L;
        given(adminRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        adminService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Admin_Details() {
        Long id = 1L;
        AdminDetailResponseDTO expected = getAdminDetailResponseDTO();

        given(adminRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(adminService.fetchDetailsById(1L), samePropertyValuesAs(expected));
    }

    @Test
    public void deleteTest() {
        Should_ThrowException_When_AdminIsNull();

        Should_SuccessFully_DeleteAdmin();
    }

    @Test
    public void Should_ThrowException_When_AdminIsNull() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(adminRepository.findAdminById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        adminService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_DeleteAdmin() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Admin admin = getAdmin();

        given(adminRepository.findAdminById(deleteRequestDTO.getId())).willReturn(of(admin));

        Admin expected = AdminUtils.convertAdminToDeleted(admin, deleteRequestDTO);

        given(adminRepository.save(expected)).willReturn(getDeletedAdminInfo());

        adminService.delete(deleteRequestDTO);

        Assertions.assertThat(adminRepository.save(expected).getStatus()).isEqualTo('D');
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_Admin_Is_Null() {
        UpdatePasswordRequestDTO requestDTO = getAdminPasswordRequestDTO();
        given(adminRepository.findAdminById(requestDTO.getId())).willReturn(Optional.empty());

        adminService.changePassword(requestDTO);
    }

    @Test(expected = OperationUnsuccessfulException.class)
    public void Should_Throw_Exception_When_Password_MisMatch() {
        UpdatePasswordRequestDTO requestDTO = new UpdatePasswordRequestDTO();
        requestDTO.setOldPassword("cogent1");

        given(adminRepository.findAdminById(requestDTO.getId())).willReturn(Optional.of(getAdmin()));

        adminService.changePassword(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Password_Duplicates() {

        UpdatePasswordRequestDTO requestDTO = new UpdatePasswordRequestDTO();
        requestDTO.setOldPassword("cogent");
        requestDTO.setNewPassword("cogent");

        given(adminRepository.findAdminById(requestDTO.getId())).willReturn(Optional.of(getAdmin()));

        thrown.expect(DataDuplicationException.class);
        adminService.changePassword(requestDTO);
    }

    @Test
    public void Should_Update_Admin_Password() {
        UpdatePasswordRequestDTO requestDTO = getAdminPasswordRequestDTO();
        given(adminRepository.findAdminById(requestDTO.getId())).willReturn(Optional.of(getAdmin()));

        adminService.changePassword(requestDTO);

        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    public void Should_Throw_Exception_When_Admin_Is_Null() {
        MultipartFile files = getMockMultipartFile();
        Long adminId = 4L;
        given(adminRepository.findAdminById(adminId)).willReturn(Optional.empty());
        thrown.expect(NoContentFoundException.class);
        adminService.updateAvatar(files, adminId);
    }

    @Test
    public void Should__Save_New_AdminAvatar() {
        MultipartFile files = getMockMultipartFile();
        Long adminId = 4L;

        AdminAvatar expected = convertFileToAdminAvatar(getUpdatedFileUploadResponse().get(0), getAdmin());

        updateAdminAvatar(adminId);
        given(adminAvatarRepository.findAdminAvatarByAdminId(adminId)).willReturn(null);

        adminService.updateAvatar(files, adminId);

        verify(adminAvatarRepository, times(1)).save(any(AdminAvatar.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "admin"})
                .matches(getUpdatedAdminAvatar()));
    }

    private void updateAdminAvatar(Long adminId) {
        given(adminRepository.findAdminById(adminId)).willReturn(Optional.of(getAdmin()));
        given(storageService.uploadFiles(any(), anyString())).willReturn(getUpdatedFileUploadResponse());
        given(adminAvatarRepository.save(any(AdminAvatar.class))).willReturn(getUpdatedAdminAvatar());
    }

    @Test
    public void Should_Update_Admin_Avatar() {
        MultipartFile files = getMockMultipartFile();
        Long adminId = 4L;
        AdminAvatar expected = convertFileToAdminAvatar(getUpdatedFileUploadResponse().get(0), getAdmin());

        updateAdminAvatar(adminId);
        given(adminAvatarRepository.findAdminAvatarByAdminId(adminId)).willReturn(getAdminAvatar());

        adminService.updateAvatar(files, adminId);

        verify(adminAvatarRepository, times(1)).save(any(AdminAvatar.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "admin"})
                .matches(getUpdatedAdminAvatar()));
    }


    @Test(expected = ConstraintViolationException.class)
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTOWithInValidInput();

        given(validator.validate(updateRequestDTO)).willThrow(ConstraintViolationException.class);

        adminService.update(updateRequestDTO, getMockMultipartFile());
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_AdminIsNull() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();
        MultipartFile files = getUpdatedMultipartFile();

        given(adminRepository.findAdminById(updateRequestDTO.getId())).willReturn(Optional.empty());
        adminService.update(updateRequestDTO, files);
    }

    @Test(expected = DataDuplicationException.class)
    public void Should_Throw_Exception_When_AdminDuplicates() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();

        given(adminRepository.findAdminById(updateRequestDTO.getId())).willReturn(Optional.of(getAdmin()));

        given(adminRepository.fetchAdmin(updateRequestDTO))
                .willReturn(getAdminObjectWithDuplicateEmailAndMobileNumber());

        adminService.update(updateRequestDTO, getUpdatedMultipartFile());
    }

    @Test
    public void Should_Throw_Exception_When_ProfileIsNotFound() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();

        updateRequestDTO.getAdminProfileUpdateRequestDTOS().forEach(
                updateDTO -> given(profileService.fetchActiveProfileById(updateDTO.getProfileId()))
                        .willThrow(NoContentFoundException.class));

        thrown.expect(NoContentFoundException.class);

        adminService.update(updateRequestDTO, getMockMultipartFile());
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_AdminCategoryIsNot_Found() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();

        given(adminCategoryService.fetchActiveAdminCategoryById(updateRequestDTO.getAdminCategoryId()))
                .willThrow(new NoContentFoundException(Admin.class));

        adminService.update(updateRequestDTO, getMockMultipartFile());
    }

    @Test
    public void Should_Successfully_Update_Admin() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();

        updateAdmin(updateRequestDTO);

        adminService.update(updateRequestDTO, getMockMultipartFile());
    }

    @Test
    public void Should_Update_Admin_Application_Module() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();

        updateAdmin(updateRequestDTO);

        verify(adminApplicationModuleRepository, times(1)).saveAll(anyIterable());

        adminService.update(updateRequestDTO, getMockMultipartFile());
    }

    private void updateAdmin(AdminUpdateRequestDTO updateRequestDTO) {

        given(adminRepository.findAdminById(updateRequestDTO.getId())).willReturn(Optional.of(getAdmin()));

        given(adminRepository.fetchAdmin(updateRequestDTO)).willReturn(new ArrayList<>());

        given(adminCategoryService.fetchActiveAdminCategoryById(updateRequestDTO.getAdminCategoryId()))
                .willReturn(getAdminCategory());
    }

    private void updateAvatar() {
        given(storageService.uploadFiles(any(), anyString())).willReturn(getUpdatedFileUploadResponse());

        given(adminAvatarRepository.save(any(AdminAvatar.class))).willReturn(getUpdatedAdminAvatar());
    }

    @Test
    public void Should_Successfully_Update_Admin_Avatar() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();
        MultipartFile files = getMockMultipartFile();

        AdminAvatar expected = convertFileToAdminAvatar(getUpdatedFileUploadResponse().get(0), getAdmin());

        updateAdmin(updateRequestDTO);

        updateAvatar();

        given(adminAvatarRepository.findAdminAvatarByAdminId(updateRequestDTO.getId())).willReturn(getAdminAvatar());

        adminService.update(updateRequestDTO, files);

        verify(adminAvatarRepository, times(1)).save(any(AdminAvatar.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "admin"})
                .matches(getUpdatedAdminAvatar()));
    }

    @Test
    public void Should__Save_New_Admin_Avatar() {
        AdminUpdateRequestDTO updateRequestDTO = getAdminUpdateRequestDTO();
        MultipartFile files = getMockMultipartFile();

        AdminAvatar expected = convertFileToAdminAvatar(getUpdatedFileUploadResponse().get(0), getAdmin());

        updateAdmin(updateRequestDTO);

        updateAvatar();

        given(adminAvatarRepository.findAdminAvatarByAdminId(updateRequestDTO.getId())).willReturn(null);

        adminService.update(updateRequestDTO, files);

        verify(adminAvatarRepository, times(1)).save(any(AdminAvatar.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "admin"})
                .matches(getUpdatedAdminAvatar()));
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_ConfirmationToken_Is_Invalid() {
        String token = getConfirmationToken();

        given(confirmationTokenRepository.findByConfirmationToken(token))
                .willThrow(NoContentFoundException.class);
        adminService.verifyConfirmationToken(token);
    }

    @Test
    public void Should_Throw_Exception_When_AdminIsRegistered() {
        String token = getConfirmationToken();
        given(confirmationTokenRepository.findByConfirmationToken(token)).willReturn(StatusConstants.INACTIVE);

        thrown.expect(BadRequestException.class);
        adminService.verifyConfirmationToken(token);
    }

    @Test
    public void savePassword_ShouldThrowException() {
        AdminPasswordRequestDTO requestDTO = getPasswordRequestDTO();

        given(confirmationTokenRepository.findAdminConfirmationTokenByToken(requestDTO.getToken()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);
        adminService.savePassword(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_Password() {
        AdminPasswordRequestDTO requestDTO = getPasswordRequestDTO();

        given(confirmationTokenRepository.findAdminConfirmationTokenByToken(requestDTO.getToken()))
                .willReturn(Optional.of(getAdminConfirmationToken()));

        adminService.savePassword(requestDTO);
        verify(confirmationTokenRepository, times(1)).save(any(AdminConfirmationToken.class));
        verify(adminRepository, times(1)).save(any(Admin.class));
    }
}

