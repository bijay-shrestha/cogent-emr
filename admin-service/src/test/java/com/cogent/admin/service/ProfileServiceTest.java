package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateRequestDTO;
import com.cogent.admin.dto.response.profile.ProfileDetailResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ProfileMenuRepository;
import com.cogent.admin.repository.ProfileRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.impl.ProfileServiceImpl;
import com.cogent.admin.utils.ProfileMenuUtils;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.ProfileMenu;
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

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.profile.ProfileRequestUtils.*;
import static com.cogent.admin.dto.profile.ProfileResponseUtils.*;
import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.getSubDepartmentInfo;
import static com.cogent.admin.utils.ProfileUtils.*;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 7/2/19
 */
public class ProfileServiceTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileMenuRepository profileMenuRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private SubDepartmentRepository subDepartmentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveProfileTest() {

        Should_ThrowException_When_ProfileNameExists();

        Should_ThrowException_When_SubDepartmentIsNotFound();

        Should_Successfully_SaveProfile();

        Should_Successfully_SaveProfileMenu();
    }

    @Test
    public void updateProfileTest() {
        Should_ThrowException_When_ProfileIsNotFound();

        Should_Throw_Exception_When_ProfileNameAlreadyExists();

        Should_ThrowException_When_SubDepartmentIsNotFound();

        Should_Successfully_UpdateProfile();

        Should_Successfully_UpdateProfileMenu();
    }

    @Test
    public void deleteProfile() {
        Should_ThrowException_When_ProfileIsNull();

        Should_SuccessFully_DeleteProfile();
    }

    @Test
    public void searchProfile() {

        Should_ThrowException_When_ProfileListIsEmpty();

        Should_Successfully_ReturnProfileList();
    }

    @Test
    public void fetchProfileDetails() {
        Should_ThrowException_When_Profile_With_Given_ID_Is_Null();

        Should_Return_Profile_Details();
    }

    @Test
    public void fetchMinimalProfiles() {
        Should_ThrowException_When_ProfilesNotFound();

        Should_Return_Profiles();
    }

    @Test
    public void Should_ThrowException_When_ProfileNameExists() {

        ProfileRequestDTO requestDTO = getProfileRequestDTOThatThrowsException();

        given(profileRepository.findProfileCountByName(requestDTO.getProfileDTO().getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        profileService.save(requestDTO);
    }

    @Test
    public void Should_ThrowException_When_SubDepartmentIsNotFound() {

        ProfileRequestDTO profileRequestDTO = getProfileRequestDTO();

        given(profileRepository.findProfileCountByName(profileRequestDTO.getProfileDTO().getName()))
                .willReturn(0L);

        given(subDepartmentRepository.findActiveSubDepartmentById(anyLong())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        profileService.save(profileRequestDTO);
    }

    @Test
    public void Should_Successfully_SaveProfile() {
        ProfileRequestDTO requestDTO = getProfileRequestDTO();

        saveProfile(requestDTO);

        Profile expected = convertDTOToProfile(requestDTO.getProfileDTO(), getSubDepartmentInfo());

        profileService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "subDepartment"})
                .matches(getProfileInfo()));

        verify(profileRepository, times(1)).save(any(expected.getClass()));
    }

    private void saveProfile(ProfileRequestDTO requestDTO) {

        given(profileRepository.findProfileCountByName(requestDTO.getProfileDTO().getName()))
                .willReturn(0L);

        given(subDepartmentRepository.findActiveSubDepartmentById(anyLong()))
                .willReturn(Optional.of(getSubDepartmentInfo()));

        given(profileRepository.save(any(Profile.class))).willReturn(getProfileInfo());
    }

    @Test
    public void Should_Successfully_SaveProfileMenu() {
        ProfileRequestDTO requestDTO = getProfileRequestDTO();

        saveProfile(requestDTO);

        List<ProfileMenu> expectedProfileMenus = ProfileMenuUtils.convertToProfileMenu(getProfileInfo(),
                requestDTO.getProfileMenuRequestDTO());

        given(profileMenuRepository.saveAll(expectedProfileMenus)).willReturn(getProfileMenu());

        profileService.save(requestDTO);

        assertThat(profileMenuRepository.saveAll(expectedProfileMenus),
                hasSize(requestDTO.getProfileMenuRequestDTO().size()));

        Assertions.assertThat(profileMenuRepository.saveAll(expectedProfileMenus).get(0).getRoleId())
                .isEqualTo(requestDTO.getProfileMenuRequestDTO().get(0).getRoleId());
    }

    @Test
    public void Should_ThrowException_When_ProfileIsNotFound() {

        ProfileUpdateRequestDTO requestDTO = getProfileRequestDTOForUpdate();

        given(profileRepository.findProfileById(requestDTO.getProfileDTO().getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        profileService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_ProfileNameAlreadyExists() {

        ProfileUpdateRequestDTO requestDTO = getProfileRequestDTOForUpdate();

        given(profileRepository.findProfileById(requestDTO.getProfileDTO().getId()))
                .willReturn(of(getProfileInfo()));

        given(profileRepository.findProfileCountByIdAndName(requestDTO.getProfileDTO().getId(),
                requestDTO.getProfileDTO().getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        profileService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_UpdateProfile() {
        ProfileUpdateRequestDTO requestDTO = getProfileRequestDTOForUpdate();

        Profile updatedProfile = convertToUpdatedProfile(
                requestDTO.getProfileDTO(), getSubDepartmentInfo(), getProfileInfo());

        updateProfile(requestDTO, updatedProfile);

        profileService.update(requestDTO);

        verify(profileRepository, times(1)).save(any(updatedProfile.getClass()));
    }

    private void updateProfile(ProfileUpdateRequestDTO requestDTO, Profile updatedProfile) {

        given(profileRepository.findProfileById(requestDTO.getProfileDTO().getId())).willReturn(of(getProfileInfo()));

        given(profileRepository.findProfileCountByIdAndName(requestDTO.getProfileDTO().getId(),
                requestDTO.getProfileDTO().getName())).willReturn(0L);

        given(subDepartmentRepository.findActiveSubDepartmentById(anyLong()))
                .willReturn(Optional.of(getSubDepartmentInfo()));

        given(profileRepository.save(updatedProfile)).willReturn(getUpdatedProfileInfo());
    }

    @Test
    public void Should_Successfully_UpdateProfileMenu() {

        ProfileUpdateRequestDTO requestDTO = getProfileRequestDTOForUpdate();

        updateProfile(requestDTO, convertToUpdatedProfile(requestDTO.getProfileDTO(), getSubDepartmentInfo(),
                getProfileInfo()));

        List<ProfileMenu> expectedProfileMenus = ProfileMenuUtils.convertToUpdatedProfileMenu(getProfileInfo(),
                requestDTO.getProfileMenuRequestDTO());

        given(profileMenuRepository.saveAll(expectedProfileMenus)).willReturn(getUpdatedProfileMenus());

        profileService.update(requestDTO);

        assertThat(profileMenuRepository.saveAll(expectedProfileMenus),
                hasSize(requestDTO.getProfileMenuRequestDTO().size()));

        Assertions.assertThat(profileMenuRepository.saveAll(expectedProfileMenus).get(0).getStatus())
                .isEqualTo(requestDTO.getProfileMenuRequestDTO().get(0).getStatus());
    }

    @Test
    public void Should_ThrowException_When_ProfileIsNull() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(profileRepository.findProfileById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        profileService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_DeleteProfile() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Profile profile = getProfileInfo();

        given(profileRepository.findProfileById(deleteRequestDTO.getId())).willReturn(of(profile));

        Profile expected = convertProfileToDeleted.apply(profile, deleteRequestDTO);

        given(profileRepository.save(expected)).willReturn(getDeletedProfileInfo());

        profileService.delete(deleteRequestDTO);

        Assertions.assertThat(profileRepository.save(expected).getStatus()).isEqualTo('D');
    }

    @Test
    public void Should_ThrowException_When_ProfileListIsEmpty() {
        Pageable pageable = PageRequest.of(1, 10);
        ProfileSearchRequestDTO searchRequestDTO = getProfileSearchRequestDTO();

        given(profileRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        profileService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_ReturnProfileList() {

        ProfileSearchRequestDTO searchRequestDTO = getProfileSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(profileRepository.search(searchRequestDTO, pageable))
                .willReturn(getProfileMinimalResponseList());

        assertThat(profileService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(getProfileMinimalResponseList()));

        assertThat(profileService.search(searchRequestDTO, pageable), hasSize(getProfileMinimalResponseList().size()));
    }

    @Test
    public void Should_ThrowException_When_ProfilesNotFound() {

        given(profileRepository.fetchActiveProfilesForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        profileService.fetchActiveProfilesForDropdown();
    }

    @Test
    public void Should_Return_Profiles() {
        given(profileRepository.fetchActiveProfilesForDropDown()).willReturn(getProfilesForDropdown());

        profileService.fetchActiveProfilesForDropdown();

        assertThat(profileService.fetchActiveProfilesForDropdown(), hasSize(getProfilesForDropdown().size()));

        assertThat(profileService.fetchActiveProfilesForDropdown(), samePropertyValuesAs(getProfilesForDropdown()));
    }

    @Test
    public void Should_ThrowException_When_Profile_With_Given_ID_Is_Null() {
        Long id = 1L;

        given(profileRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        profileService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Profile_Details() {
        Long id = 1L;

        ProfileDetailResponseDTO expected = getProfileDetailResponse();

        given(profileRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(profileService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }
}
