package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctor.*;
import com.cogent.admin.dto.response.doctor.DoctorDetailResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorMinimalResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorUpdateResponseDTO;
import com.cogent.admin.exception.ConstraintViolationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.feign.service.FileServerService;
import com.cogent.admin.repository.*;
import com.cogent.admin.service.impl.DoctorServiceImpl;
import com.cogent.persistence.model.Doctor;
import com.cogent.persistence.model.DoctorAvatar;
import com.cogent.persistence.model.DoctorSignature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.country.CountryResponseUtils.fetchCountry;
import static com.cogent.admin.dto.department.DepartmentTestUtils.getDepartmentInfo;
import static com.cogent.admin.dto.doctor.DoctorRequestUtils.*;
import static com.cogent.admin.dto.doctor.DoctorResponseUtils.*;
import static com.cogent.admin.dto.doctorType.DoctorTypeResponseUtils.fetchDoctorType;
import static com.cogent.admin.dto.files.FileServerResponseUtils.getFileServerResponse;
import static com.cogent.admin.dto.files.FileServerResponseUtils.getUpdatedFileServerResponse;
import static com.cogent.admin.dto.files.MultipartFileUtils.getMockMultipartFile;
import static com.cogent.admin.dto.gender.GenderResponseUtils.fetchGender;
import static com.cogent.admin.dto.request.qualification.QualificationResponseUtils.fetchQualification;
import static com.cogent.admin.dto.specialization.SpecializationResponseUtils.getSpecialization;
import static com.cogent.admin.utils.DoctorUtils.convertFileToDoctorAvatar;
import static com.cogent.admin.utils.DoctorUtils.convertFileToDoctorSignature;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author smriti on 2019-09-29
 */
public class DoctorServiceTest {
    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private SpecializationService specializationService;

    @Mock
    private DoctorSpecializationRepository doctorSpecializationRepository;

    @Mock
    private DoctorAvatarRepository doctorAvatarRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DoctorDepartmentRepository doctorDepartmentRepository;

    @Mock
    private Validator validator;

    @Mock
    private FileServerService fileServerService;

    @Mock
    private GenderService genderService;

    @Mock
    private CountryService countryService;

    @Mock
    private QualificationService qualificationService;

    @Mock
    private DoctorTypeService doctorTypeService;

    @Mock
    private DoctorScheduleTypeRepository doctorScheduleTypeRepository;

    @Mock
    private DoctorQualificationRepository doctorQualificationRepository;

    @Mock
    private DoctorSignatureRepository doctorSignatureRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_InputIsInvalidForSave();
        Should_Throw_Exception_When_GenderNotFound();
        Should_Throw_Exception_When_CountryNotFound();
        Should_Successfully_Save_Doctor();
        Should_Successfully_Save_DoctorSpecialization();
        Should_Successfully_Save_DoctorDepartment();
        Should_Successfully_Save_DoctorScheduleType();
        Should_Successfully_Save_DoctorQualification();
        Should_Successfully_Save_DoctorAvatar();
        Should_Successfully_Save_DoctorSignature();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_InputIsInvalidForUpdate();
        Should_Throw_Exception_When_DoctorNotFound();
        Should_Successfully_Update_Doctor();
        Should_Successfully_Update_DoctorSpecialization();
        Should_Successfully_Update_DoctorDepartment();
        Should_Successfully_Update_DoctorScheduleType();
        Should_Successfully_Update_Doctor_Signature();
        Should_Successfully_Update_Doctor_Avatar();
        Should_Successfully_Update_Doctor_Signature();
    }

    @Test
    public void deleteTest() {
        deleteDoctor_ShouldThrowException();
        Should_SuccessFully_Delete_Doctor();
    }

    @Test
    public void searchTest() {
        searchDoctor_ShouldThrowException();
        Should_Successfully_Return_DoctorList();
    }

    @Test
    public void dropdownTest() {
        fetchDoctorForDropdown_ShouldThrowException();
        Should_Successfully_Return_Doctor();
    }

    @Test
    public void detailsTest() {
        fetchDoctorDetails_ShouldThrowException();
        Should_Return_Doctor_Details();
    }

    @Test
    public void fetchDetailsForUpdate() {
        Should_Return_Doctor_Details_For_Update();
    }

    @Test
    public void fetchActiveDoctor() {
        fetchActiveDoctor_ShouldThrowException();
        Should_Return_ActiveDoctor();
    }

    @Test
    public void fetchDoctorBySpecializationId() {
        fetchDoctorBySpecializationId_ShouldThrowException();
        Should_Return_DoctorBySpecializationId();
    }

    @Test(expected = ConstraintViolationException.class)
    public void Should_Throw_Exception_When_InputIsInvalidForSave() {
        DoctorRequestDTO requestDTO = getInvalidInputForSave();

        given(validator.validate(requestDTO)).willThrow(ConstraintViolationException.class);
        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());
    }

    @Test
    public void Should_Throw_Exception_When_GenderNotFound() {
        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        given(genderService.fetchGenderById(requestDTO.getGenderId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());
    }

    @Test
    public void Should_Throw_Exception_When_CountryNotFound() {
        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());
    }

    @Test
    public void Should_Successfully_Save_Doctor() {

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorRepository, times(1))
                .save(ArgumentMatchers.any(Doctor.class));
    }

    @Test
    public void Should_Successfully_Save_DoctorSpecialization() {

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        saveDoctorSpecialization(requestDTO);

        verify(doctorSpecializationRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Save_DoctorDepartment() {

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        saveDoctorSpecialization(requestDTO);

        saveDoctorDepartment(requestDTO);

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorDepartmentRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Save_DoctorScheduleType() {

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        saveDoctorSpecialization(requestDTO);

        saveDoctorDepartment(requestDTO);

        saveDoctorScheduleType(requestDTO);

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorScheduleTypeRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Save_DoctorQualification() {

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        saveDoctorSpecialization(requestDTO);

        saveDoctorDepartment(requestDTO);

        saveDoctorScheduleType(requestDTO);

        saveDoctorQualification(requestDTO);

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorQualificationRepository, times(1))
                .saveAll(anyIterable());
    }

    private void saveDoctor(DoctorRequestDTO requestDTO) {

        given(doctorRepository.save(any(Doctor.class))).willReturn(getDoctor());

        given(genderService.fetchGenderById(requestDTO.getGenderId()))
                .willReturn(fetchGender());

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willReturn(fetchCountry());
    }

    private void saveDoctorSpecialization(DoctorRequestDTO requestDTO) {
        requestDTO.getSpecializationIds().forEach(
                id -> given(specializationService.fetchActiveSpecializationById(id))
                        .willReturn(getSpecialization()));
    }

    private void saveDoctorDepartment(DoctorRequestDTO requestDTO) {
        requestDTO.getDepartmentIds().forEach(
                id -> given(departmentRepository.findActiveDepartmentById(id))
                        .willReturn(Optional.of(getDepartmentInfo())));
    }

    private void saveDoctorScheduleType(DoctorRequestDTO requestDTO) {
        requestDTO.getDoctorTypeIds().forEach(
                id -> given(doctorTypeService.fetchDoctorTypeById(id))
                        .willReturn(fetchDoctorType()));
    }

    private void saveDoctorQualification(DoctorRequestDTO requestDTO) {
        requestDTO.getQualificationIds().forEach(
                id -> given(qualificationService.fetchQualificationById(id))
                        .willReturn(fetchQualification()));
    }

    private void saveDoctorAvatar() {
        given(fileServerService.uploadFiles(any(), anyString())).willReturn(getFileServerResponse());

        given(doctorAvatarRepository.save(any(DoctorAvatar.class))).willReturn(getDoctorAvatar());
    }

    private void saveDoctorSignature() {
        given(fileServerService.uploadFiles(any(), anyString())).willReturn(getFileServerResponse());

        given(doctorSignatureRepository.save(any(DoctorSignature.class))).willReturn(getDoctorSignature());
    }

    @Test
    public void Should_Successfully_Save_DoctorAvatar() {
        Should_Throw_Exception_When_DoctorNotFound();

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        saveDoctorAvatar();

        DoctorAvatar expected = convertFileToDoctorAvatar(
                getFileServerResponse().get(0), getDoctor());

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorAvatarRepository, Mockito.times(1)).save(any(DoctorAvatar.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "Doctor"})
                .matches(getDoctorAvatar()));
    }

    @Test
    public void Should_Successfully_Save_DoctorSignature() {
        Should_Throw_Exception_When_DoctorNotFound();

        DoctorRequestDTO requestDTO = getDoctorRequestDTO();

        saveDoctor(requestDTO);

        saveDoctorSignature();

        DoctorSignature expected = convertFileToDoctorSignature(
                getFileServerResponse().get(0), getDoctor());

        doctorService.save(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorSignatureRepository, Mockito.times(1))
                .save(any(DoctorSignature.class));

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "Doctor"})
                .matches(getDoctorSignature()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() {
        DoctorUpdateRequestDTO updateRequestDTO = getInvalidInputForUpdate();

        given(validator.validate(updateRequestDTO)).willThrow(ConstraintViolationException.class);

        doctorService.update(updateRequestDTO, getMockMultipartFile(), getMockMultipartFile());
    }

    @Test
    public void Should_Throw_Exception_When_DoctorNotFound() {
        DoctorUpdateRequestDTO requestDTO = getDoctorUpdateRequestDTO();

        given(doctorRepository.findDoctorById(requestDTO.getUpdateDTO().getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);
        doctorService.update(requestDTO, getMockMultipartFile(), getMockMultipartFile());
    }

    @Test
    public void Should_Successfully_Update_Doctor() {

        DoctorUpdateRequestDTO requestDTO = getDoctorUpdateRequestDTO();

        given(doctorRepository.findDoctorById(requestDTO.getUpdateDTO().getId()))
                .willReturn(Optional.of(getDoctor()));

        doctorService.update(requestDTO, getMockMultipartFile(), getMockMultipartFile());
    }

    @Test
    public void Should_Successfully_Update_DoctorSpecialization() {

        DoctorUpdateRequestDTO requestDTO = getDoctorUpdateRequestDTO();

        updateDoctor(requestDTO.getUpdateDTO());

        updateDoctorSpecialization(requestDTO.getSpecializationUpdateRequestDTOS());

        doctorService.update(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorSpecializationRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Update_DoctorDepartment() {
        DoctorUpdateRequestDTO requestDTO = getDoctorUpdateRequestDTO();

        updateDoctor(requestDTO.getUpdateDTO());
        updateDoctorSpecialization(requestDTO.getSpecializationUpdateRequestDTOS());

        updateDoctorDepartment(requestDTO.getDoctorDepartmentUpdateDTOS());

        doctorService.update(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorDepartmentRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Update_DoctorScheduleType() {
        DoctorUpdateRequestDTO requestDTO = getDoctorUpdateRequestDTO();

        updateDoctor(requestDTO.getUpdateDTO());
        updateDoctorSpecialization(requestDTO.getSpecializationUpdateRequestDTOS());
        updateDoctorDepartment(requestDTO.getDoctorDepartmentUpdateDTOS());

        updateDoctorScheduleType(requestDTO.getDoctorScheduleTypeUpdateDTOS());

        doctorService.update(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorScheduleTypeRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Update_DoctorQualification() {
        DoctorUpdateRequestDTO requestDTO = getDoctorUpdateRequestDTO();

        updateDoctor(requestDTO.getUpdateDTO());
        updateDoctorSpecialization(requestDTO.getSpecializationUpdateRequestDTOS());
        updateDoctorDepartment(requestDTO.getDoctorDepartmentUpdateDTOS());
        updateDoctorScheduleType(requestDTO.getDoctorScheduleTypeUpdateDTOS());

        updateDoctorQualification(requestDTO.getDoctorQualificationUpdateDTOS());

        doctorService.update(requestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorQualificationRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Update_Doctor_Avatar() {
        DoctorUpdateRequestDTO updateRequestDTO = getDoctorUpdateRequestDTO();

        updateDoctor(updateRequestDTO.getUpdateDTO());
        updateDoctorSpecialization(updateRequestDTO.getSpecializationUpdateRequestDTOS());
        updateDoctorDepartment(updateRequestDTO.getDoctorDepartmentUpdateDTOS());
        updateDoctorScheduleType(updateRequestDTO.getDoctorScheduleTypeUpdateDTOS());

        updateDoctorQualification(updateRequestDTO.getDoctorQualificationUpdateDTOS());

        updateDoctorAvatar();

        given(doctorAvatarRepository.findByDoctorId(updateRequestDTO.getUpdateDTO().getId()))
                .willReturn(getDoctorAvatar());

        doctorService.update(updateRequestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorAvatarRepository, Mockito.times(1))
                .save(any(DoctorAvatar.class));
    }

    @Test
    public void Should_Successfully_Update_Doctor_Signature() {
        DoctorUpdateRequestDTO updateRequestDTO = getDoctorUpdateRequestDTO();

        updateDoctor(updateRequestDTO.getUpdateDTO());
        updateDoctorSpecialization(updateRequestDTO.getSpecializationUpdateRequestDTOS());
        updateDoctorDepartment(updateRequestDTO.getDoctorDepartmentUpdateDTOS());
        updateDoctorScheduleType(updateRequestDTO.getDoctorScheduleTypeUpdateDTOS());
        updateDoctorQualification(updateRequestDTO.getDoctorQualificationUpdateDTOS());
        updateDoctorAvatar();

        given(doctorSignatureRepository.findByDoctorId(updateRequestDTO.getUpdateDTO().getId()))
                .willReturn(getDoctorSignature());

        doctorService.update(updateRequestDTO, getMockMultipartFile(), getMockMultipartFile());

        verify(doctorSignatureRepository, Mockito.times(1))
                .save(any(DoctorSignature.class));
    }

    private void updateDoctor(DoctorUpdateDTO updateRequestDTO) {

        given(doctorRepository.findDoctorById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getDoctor()));

        given(genderService.fetchGenderById(updateRequestDTO.getId()))
                .willReturn(fetchGender());

        given(countryService.fetchCountryById(updateRequestDTO.getCountryId()))
                .willReturn(fetchCountry());

        given(doctorRepository.save(any(Doctor.class))).willReturn(getUpdatedDoctor());
    }

    private void updateDoctorSpecialization(List<DoctorSpecializationUpdateDTO> updateDTOS) {
        updateDTOS.forEach(updateDTO ->
                given(specializationService.fetchActiveSpecializationById(updateDTO.getSpecializationId()))
                        .willReturn(getSpecialization()));
    }

    private void updateDoctorDepartment(List<DoctorDepartmentUpdateDTO> updateDTOS) {
        updateDTOS.forEach(updateDTO ->
                given(departmentRepository.findActiveDepartmentById(updateDTO.getDepartmentId()))
                        .willReturn(Optional.of(getDepartmentInfo())));
    }

    private void updateDoctorScheduleType(List<DoctorScheduleTypeUpdateDTO> updateDTOS) {
        updateDTOS.forEach(updateDTO ->
                given(doctorTypeService.fetchDoctorTypeById(updateDTO.getDoctorTypeId()))
                        .willReturn(fetchDoctorType()));
    }

    private void updateDoctorQualification(List<DoctorQualificationUpdateDTO> updateDTOS) {
        updateDTOS.forEach(updateDTO ->
                given(qualificationService.fetchQualificationById(updateDTO.getQualificationId()))
                        .willReturn(fetchQualification()));
    }

    private void updateDoctorAvatar() {
        given(fileServerService.uploadFiles(any(), anyString())).willReturn(getUpdatedFileServerResponse());

        given(doctorAvatarRepository.save(any(DoctorAvatar.class))).willReturn(getDoctorAvatar());
    }

    @Test
    public void deleteDoctor_ShouldThrowException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(doctorRepository.findDoctorById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        doctorService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_Doctor() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        Doctor Doctor = getDoctor();

        given(doctorRepository.findDoctorById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(Doctor));

        doctorService.delete(deleteRequestDTO);
    }

    @Test
    public void searchDoctor_ShouldThrowException() {
        Pageable pageable = PageRequest.of(1, 10);
        DoctorSearchRequestDTO searchRequestDTO = getDoctorSearchRequestDTO();

        given(doctorRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        doctorService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_DoctorList() {
        DoctorSearchRequestDTO searchRequestDTO = getDoctorSearchRequestDTO();
        List<DoctorMinimalResponseDTO> expected = fetchDoctorMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(doctorRepository.search(searchRequestDTO, pageable)).willReturn(expected);

        assertThat(doctorService.search(searchRequestDTO, pageable), samePropertyValuesAs(expected));

        assertThat(doctorService.search(searchRequestDTO, pageable), hasSize(expected.size()));
    }

    @Test
    public void fetchDoctorForDropdown_ShouldThrowException() {

        given(doctorRepository.fetchDoctorForDropdown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorService.fetchDoctorForDropdown();
    }

    @Test
    public void Should_Successfully_Return_Doctor() {
        given(doctorRepository.fetchDoctorForDropdown()).willReturn(fetchDoctorForDropDown());

        doctorService.fetchDoctorForDropdown();

        assertThat(doctorService.fetchDoctorForDropdown(), hasSize(fetchDoctorForDropDown().size()));

        assertThat(doctorService.fetchDoctorForDropdown(), samePropertyValuesAs(fetchDoctorForDropDown()));
    }

    @Test
    public void fetchDoctorDetails_ShouldThrowException() {
        Long id = 1L;

        given(doctorRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Doctor_Details() {
        Long id = 1L;
        DoctorDetailResponseDTO expected = fetchDoctorDetailResponseDTO();

        given(doctorRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(doctorService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test
    public void Should_Return_Doctor_Details_For_Update() {
        Long id = 1L;
        DoctorUpdateResponseDTO expected = fetchDoctorUpdateResponseDTO();

        given(doctorRepository.fetchDetailsForUpdate(id)).willReturn(expected);

        assertThat(doctorService.fetchDetailsForUpdate(id), samePropertyValuesAs(expected));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchActiveDoctor_ShouldThrowException() {
        Long id = 1L;

        given(doctorRepository.findActiveDoctorById(id))
                .willThrow(NoContentFoundException.class);

        doctorService.fetchDoctorById(id);
    }

    @Test
    public void Should_Return_ActiveDoctor() {
        Long id = 1L;
        Doctor doctor = getDoctor();

        given(doctorRepository.findActiveDoctorById(id))
                .willReturn(Optional.of(doctor));

        Assert.assertThat(doctorService.fetchDoctorById(id), samePropertyValuesAs(doctor));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDoctorBySpecializationId_ShouldThrowException() {
        Long specializationId = 1L;

        given(doctorRepository.fetchDoctorBySpecializationId(specializationId))
                .willThrow(NoContentFoundException.class);

        doctorService.fetchDoctorBySpecializationId(specializationId);
    }

    @Test
    public void Should_Return_DoctorBySpecializationId() {
        Long specializationId = 1L;

        given(doctorRepository.fetchDoctorBySpecializationId(specializationId))
                .willReturn(fetchDoctorForDropDown());

        doctorService.fetchDoctorBySpecializationId(specializationId);

        assertThat(doctorService.fetchDoctorBySpecializationId(specializationId),
                hasSize(fetchDoctorForDropDown().size()));

        assertThat(doctorService.fetchDoctorBySpecializationId(specializationId),
                samePropertyValuesAs(fetchDoctorForDropDown()));
    }
}
