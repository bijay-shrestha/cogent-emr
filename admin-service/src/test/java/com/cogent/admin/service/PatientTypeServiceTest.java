package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.PatientTypeRepository;
import com.cogent.admin.service.impl.PatientTypeServiceImpl;
import com.cogent.persistence.model.PatientType;
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
import static com.cogent.admin.dto.request.patientType.PatientTypeRequestUtils.*;
import static com.cogent.admin.dto.request.patientType.PatientTypeResponseUtils.*;
import static com.cogent.admin.utils.PatientTypeUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author smriti on 2019-09-26
 */
public class PatientTypeServiceTest {

    @InjectMocks
    private PatientTypeServiceImpl patientTypeService;

    @Mock
    private PatientTypeRepository patientTypeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();

        Should_Successfully_Save_PatientType();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_PatientType_NotFound();

        Should_Throw_Exception_When_Name_Already_Exists();

        Should_Successfully_Update_PatientType();
    }

    @Test
    public void deleteTest() {

        deletePatientType_ShouldThrowException();

        Should_SuccessFully_Delete_PatientType();
    }

    @Test
    public void searchTest() {

        searchPatientType_ShouldThrowException();

        Should_Successfully_Return_PatientTypeList();
    }

    @Test
    public void dropdownTest() {
        fetchPatientType_ShouldThrowException();

        Should_Successfully_Return_PatientType();
    }

    @Test
    public void detailsTest() {
        fetchPatientTypeDetails_ShouldThrowException();

        Should_Return_PatientType_Details();
    }

    @Test
    public void fetchActivePatientType() {
        fetchActivePatientType_ShouldThrowException();
        Should_Return_ActiveAppointmentType();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        PatientTypeRequestDTO requestDTO = getPatientTypeRequestDTO();

        given(patientTypeRepository.fetchPatientTypeByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        patientTypeService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_PatientType() {
        PatientTypeRequestDTO requestDTO = getPatientTypeRequestDTO();

        PatientType expected = convertDTOToPatientType(requestDTO);

        given(patientTypeRepository.fetchPatientTypeByName(requestDTO.getName()))
                .willReturn(0L);

        patientTypeService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getPatientType()));

        verify(patientTypeRepository, times(1))
                .save(ArgumentMatchers.any(PatientType.class));
    }

    @Test
    public void Should_Throw_Exception_When_PatientType_NotFound() {
        PatientTypeUpdateRequestDTO requestDTO = getPatientTypeUpdateRequestDTO();

        given(patientTypeRepository.findPatientTypeById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        patientTypeService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        PatientTypeUpdateRequestDTO requestDTO = getPatientTypeUpdateRequestDTO();

        given(patientTypeRepository.findPatientTypeById(requestDTO.getId()))
                .willReturn(Optional.of(getPatientType()));

        given(patientTypeRepository.fetchPatientTypeByIdAndName(
                requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        patientTypeService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_PatientType() {

        PatientTypeUpdateRequestDTO requestDTO = getPatientTypeUpdateRequestDTO();
        PatientType expected = convertToUpdatedPatientType(
                requestDTO, getPatientType());

        given(patientTypeRepository.findPatientTypeById(requestDTO.getId()))
                .willReturn(Optional.of(getPatientType()));

        given(patientTypeRepository.fetchPatientTypeByIdAndName(requestDTO.getId(),
                requestDTO.getName())).willReturn(0L);

        patientTypeService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"})
                .matches(getUpdatedPatientType()));
    }

    @Test
    public void deletePatientType_ShouldThrowException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(patientTypeRepository.findPatientTypeById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        patientTypeService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_PatientType() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        PatientType patientType = getPatientType();

        given(patientTypeRepository.findPatientTypeById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(patientType));

        PatientType expected = convertToDeletedPatientType(patientType, deleteRequestDTO);

        given(patientTypeRepository.save(expected)).willReturn(getDeletedPatientType());

        patientTypeService.delete(deleteRequestDTO);

        assertTrue(patientTypeRepository.save(expected).getStatus().equals('D') &&
                patientTypeRepository.save(expected).getRemarks().equals(getDeletedPatientType().getRemarks()));
    }

    @Test
    public void searchPatientType_ShouldThrowException() {
        Pageable pageable = PageRequest.of(1, 10);
        PatientTypeSearchRequestDTO searchRequestDTO = getPatientTypeSearchRequestDTO();

        given(patientTypeRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        patientTypeService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_PatientTypeList() {
        PatientTypeSearchRequestDTO searchRequestDTO = getPatientTypeSearchRequestDTO();
        List<PatientTypeMinimalResponseDTO> expected = fetchPatientTypeMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(patientTypeRepository.search(searchRequestDTO, pageable)).willReturn(expected);

        assertThat(patientTypeService.search(searchRequestDTO, pageable), samePropertyValuesAs(expected));

        assertThat(patientTypeService.search(searchRequestDTO, pageable), hasSize(expected.size()));
    }

    @Test
    public void fetchPatientType_ShouldThrowException() {

        given(patientTypeRepository.fetchActivePatientTypeForDropdown())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        patientTypeService.fetchActivePatientTypeForDropdown();
    }

    @Test
    public void Should_Successfully_Return_PatientType() {
        given(patientTypeRepository.fetchActivePatientTypeForDropdown())
                .willReturn(fetchPatientTypeForDropDown());

        patientTypeService.fetchActivePatientTypeForDropdown();

        assertThat(patientTypeService.fetchActivePatientTypeForDropdown(),
                hasSize(fetchPatientTypeForDropDown().size()));

        assertThat(patientTypeService.fetchActivePatientTypeForDropdown(),
                samePropertyValuesAs(fetchPatientTypeForDropDown()));
    }

    @Test
    public void fetchPatientTypeDetails_ShouldThrowException() {
        Long id = 1L;

        given(patientTypeRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        patientTypeService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_PatientType_Details() {
        Long id = 1L;
        PatientTypeResponseDTO expected = fetchPatientTypeResponseDTO();

        given(patientTypeRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(patientTypeService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchActivePatientType_ShouldThrowException() {
        Long id = 1L;

        given(patientTypeRepository.findActivePatientTypeById(id))
                .willThrow(NoContentFoundException.class);

        patientTypeService.fetchPatientTypeById(id);
    }

    @Test
    public void Should_Return_ActiveAppointmentType() {
        Long id = 1L;
        PatientType patientType = getPatientType();

        given(patientTypeRepository.findActivePatientTypeById(id))
                .willReturn(Optional.of(patientType));

        assertThat(patientTypeService.fetchPatientTypeById(id), samePropertyValuesAs(patientType));
    }
}
