package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;
import com.cogent.admin.dto.response.specialization.SpecializationMinimalResponseDTO;
import com.cogent.admin.dto.response.specialization.SpecializationResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.SpecializationRepository;
import com.cogent.admin.service.impl.SpecializationServiceImpl;
import com.cogent.persistence.model.Specialization;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.doctor.DoctorResponseUtils.fetchDoctorForDropDown;
import static com.cogent.admin.dto.specialization.SpecializationRequestUtils.*;
import static com.cogent.admin.dto.specialization.SpecializationResponseUtils.*;
import static com.cogent.admin.utils.SpecializationUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author smriti on 2019-09-25
 */
public class SpecializationServiceTest {
    @InjectMocks
    private SpecializationServiceImpl specializationService;

    @Mock
    private SpecializationRepository specializationRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();

        Should_Successfully_Save_Specialization();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_Specialization_NotFound();

        Should_Throw_Exception_When_Name_Already_Exists();

        Should_Successfully_Update_Specialization();
    }

    @Test
    public void deleteTest() {

        deleteSpecialization_ShouldThrowException();

        Should_SuccessFully_Delete_Specialization();
    }

    @Test
    public void searchTest() {

        searchSpecialization_ShouldThrowException();

        Should_Successfully_Return_SpecializationList();
    }

    @Test
    public void dropdownTest() {
        fetchSpecializationDropdown_ShouldThrowException();

        Should_Successfully_Return_Specialization();
    }

    @Test
    public void detailsTest() {
        fetchSpecializationDetails_ShouldThrowException();

        Should_Return_Specialization_Details();
    }

    @Test
    public void fetchDoctorBySpecializationId() {
        fetchSpecializationByDoctorId_ShouldThrowException();
        Should_Return_DoctorBySpecializationId();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        SpecializationRequestDTO requestDTO = getSpecializationRequestDTO();

        given(specializationRepository.fetchSpecializationByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        specializationService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_Specialization() {
        SpecializationRequestDTO requestDTO = getSpecializationRequestDTO();

        Specialization expected = convertDTOToSpecialization(requestDTO);

        given(specializationRepository.fetchSpecializationByName(requestDTO.getName()))
                .willReturn(0L);

        specializationService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getSpecialization()));

        verify(specializationRepository, times(1))
                .save(ArgumentMatchers.any(Specialization.class));
    }

    @Test
    public void Should_Throw_Exception_When_Specialization_NotFound() {
        SpecializationUpdateRequestDTO requestDTO = getSpecializationUpdateRequestDTO();

        given(specializationRepository.findSpecializationById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        specializationService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        SpecializationUpdateRequestDTO requestDTO = getSpecializationUpdateRequestDTO();

        given(specializationRepository.findSpecializationById(requestDTO.getId()))
                .willReturn(Optional.of(getSpecialization()));

        given(specializationRepository.fetchSpecializationByIdAndName(
                requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        specializationService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_Specialization() {

        SpecializationUpdateRequestDTO requestDTO = getSpecializationUpdateRequestDTO();
        Specialization expected = convertToUpdatedSpecialization(requestDTO, getSpecialization());

        given(specializationRepository.findSpecializationById(requestDTO.getId()))
                .willReturn(Optional.of(getSpecialization()));

        given(specializationRepository.fetchSpecializationByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(0L);

        specializationService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"})
                .matches(getUpdatedSpecialization()));

        verify(specializationRepository, Mockito.times(1)).save(any(expected.getClass()));
    }

    @Test
    public void deleteSpecialization_ShouldThrowException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(specializationRepository.findSpecializationById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        specializationService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_Specialization() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        Specialization specialization = getSpecialization();

        given(specializationRepository.findSpecializationById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(specialization));

        Specialization expected = convertToDeletedSpecialization(specialization, deleteRequestDTO);

        given(specializationRepository.save(expected)).willReturn(getDeletedSpecialization());

        specializationService.delete(deleteRequestDTO);

        assertTrue(specializationRepository.save(expected).getStatus().equals('D') &&
                specializationRepository.save(expected).getRemarks().equals(getDeletedSpecialization().getRemarks()));

        verify(specializationRepository, times(3)).save(any(expected.getClass()));
    }

    @Test
    public void searchSpecialization_ShouldThrowException() {
        Pageable pageable = PageRequest.of(1, 10);
        SpecializationSearchRequestDTO searchRequestDTO = getSpecializationSearchRequestDTO();

        given(specializationRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        specializationService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_SpecializationList() {
        SpecializationSearchRequestDTO searchRequestDTO = getSpecializationSearchRequestDTO();
        List<SpecializationMinimalResponseDTO> expected = fetchSpecializationMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(specializationRepository.search(searchRequestDTO, pageable)).willReturn(expected);

        assertThat(specializationService.search(searchRequestDTO, pageable), samePropertyValuesAs(expected));

        assertThat(specializationService.search(searchRequestDTO, pageable), hasSize(expected.size()));
    }

    @Test
    public void fetchSpecializationDropdown_ShouldThrowException() {

        given(specializationRepository.fetchActiveSpecializationForDropDown())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        specializationService.fetchActiveSpecializationForDropDown();
    }

    @Test
    public void Should_Successfully_Return_Specialization() {
        given(specializationRepository.fetchActiveSpecializationForDropDown())
                .willReturn(fetchSpecializationForDropDown());

        specializationService.fetchActiveSpecializationForDropDown();

        assertThat(specializationService.fetchActiveSpecializationForDropDown(),
                hasSize(fetchSpecializationForDropDown().size()));

        assertThat(specializationService.fetchActiveSpecializationForDropDown(),
                samePropertyValuesAs(fetchSpecializationForDropDown()));
    }

    @Test
    public void fetchSpecializationDetails_ShouldThrowException() {
        Long id = 1L;

        given(specializationRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        specializationService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Specialization_Details() {
        Long id = 1L;
        SpecializationResponseDTO expected = fetchSpecializationResponseDTO();

        given(specializationRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(specializationService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchSpecializationByDoctorId_ShouldThrowException() {
        Long DoctorId = 1L;

        given(specializationRepository.fetchSpecializationByDoctorId(DoctorId))
                .willThrow(NoContentFoundException.class);

        specializationService.fetchSpecializationByDoctorId(DoctorId);
    }

    @Test
    public void Should_Return_DoctorBySpecializationId() {
        Long doctorId = 1L;

        given(specializationRepository.fetchSpecializationByDoctorId(doctorId))
                .willReturn(fetchSpecializationForDropDown());

        specializationService.fetchSpecializationByDoctorId(doctorId);

        MatcherAssert.assertThat(specializationService.fetchSpecializationByDoctorId(doctorId),
                hasSize(fetchDoctorForDropDown().size()));

        MatcherAssert.assertThat(specializationService.fetchSpecializationByDoctorId(doctorId),
                samePropertyValuesAs(fetchDoctorForDropDown()));
    }
}
