package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AppointmentTypeRepository;
import com.cogent.admin.service.impl.AppointmentTypeServiceImpl;
import com.cogent.persistence.model.AppointmentType;
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
import static com.cogent.admin.dto.appointmentType.AppointmentTypeRequestUtils.*;
import static com.cogent.admin.dto.appointmentType.AppointmentTypeResponseUtils.*;
import static com.cogent.admin.utils.AppointmentTypeUtils.*;
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
public class AppointmentTypeServiceTest {

    @InjectMocks
    private AppointmentTypeServiceImpl appointmentTypeService;

    @Mock
    private AppointmentTypeRepository appointmentTypeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();

        Should_Successfully_Save_AppointmentType();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_AppointmentType_NotFound();

        Should_Throw_Exception_When_Name_Already_Exists();

        Should_Successfully_Update_AppointmentType();
    }

    @Test
    public void deleteTest() {

        deleteAppointmentType_ShouldThrowException();

        Should_SuccessFully_Delete_AppointmentType();
    }

    @Test
    public void searchTest() {

        searchAppointmentType_ShouldThrowException();

        Should_Successfully_Return_AppointmentTypeList();
    }

    @Test
    public void dropdownTest() {
        fetchAppointmentType_ShouldThrowException();

        Should_Successfully_Return_AppointmentType();
    }

    @Test
    public void detailsTest() {
        fetchAppointmentTypeDetails_ShouldThrowException();

        Should_Return_AppointmentType_Details();
    }

    @Test
    public void fetchActiveAppointmentType() {
        fetchAppointmentTypeById_ShouldThrowException();
        Should_Return_fetchAppointmentTypeById();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        AppointmentTypeRequestDTO requestDTO = getAppointmentTypeRequestDTO();

        given(appointmentTypeRepository.fetchAppointmentTypeByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        appointmentTypeService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_AppointmentType() {
        AppointmentTypeRequestDTO requestDTO = getAppointmentTypeRequestDTO();

        AppointmentType expected = convertDTOToAppointmentType(requestDTO);

        given(appointmentTypeRepository.fetchAppointmentTypeByName(requestDTO.getName()))
                .willReturn(0L);

        appointmentTypeService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getAppointmentType()));

        verify(appointmentTypeRepository, times(1))
                .save(ArgumentMatchers.any(AppointmentType.class));
    }

    @Test
    public void Should_Throw_Exception_When_AppointmentType_NotFound() {
        AppointmentTypeUpdateRequestDTO requestDTO = getAppointmentTypeUpdateRequestDTO();

        given(appointmentTypeRepository.findAppointmentTypeById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        appointmentTypeService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        AppointmentTypeUpdateRequestDTO requestDTO = getAppointmentTypeUpdateRequestDTO();

        given(appointmentTypeRepository.findAppointmentTypeById(requestDTO.getId()))
                .willReturn(Optional.of(getAppointmentType()));

        given(appointmentTypeRepository.fetchAppointmentTypeByIdAndName(
                requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        appointmentTypeService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_AppointmentType() {

        AppointmentTypeUpdateRequestDTO requestDTO = getAppointmentTypeUpdateRequestDTO();
        AppointmentType expected = convertToUpdatedAppointmentType(
                requestDTO, getAppointmentType());

        given(appointmentTypeRepository.findAppointmentTypeById(requestDTO.getId()))
                .willReturn(Optional.of(getAppointmentType()));

        given(appointmentTypeRepository.fetchAppointmentTypeByIdAndName(requestDTO.getId(),
                requestDTO.getName())).willReturn(0L);

        appointmentTypeService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"})
                .matches(getUpdatedAppointmentType()));
    }

    @Test
    public void deleteAppointmentType_ShouldThrowException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(appointmentTypeRepository.findAppointmentTypeById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        appointmentTypeService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_AppointmentType() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        AppointmentType appointmentType = getAppointmentType();

        given(appointmentTypeRepository.findAppointmentTypeById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(appointmentType));

        AppointmentType expected = convertToDeletedAppointmentType(appointmentType, deleteRequestDTO);

        given(appointmentTypeRepository.save(expected)).willReturn(getDeletedAppointmentType());

        appointmentTypeService.delete(deleteRequestDTO);

        assertTrue(appointmentTypeRepository.save(expected).getStatus().equals('D') &&
                appointmentTypeRepository.save(expected).getRemarks().equals(getDeletedAppointmentType().getRemarks()));
    }

    @Test
    public void searchAppointmentType_ShouldThrowException() {
        Pageable pageable = PageRequest.of(1, 10);
        AppointmentTypeSearchRequestDTO searchRequestDTO = getAppointmentTypeSearchRequestDTO();

        given(appointmentTypeRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        appointmentTypeService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_AppointmentTypeList() {
        AppointmentTypeSearchRequestDTO searchRequestDTO = getAppointmentTypeSearchRequestDTO();
        List<AppointmentTypeMinimalResponseDTO> expected = fetchAppointmentTypeMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(appointmentTypeRepository.search(searchRequestDTO, pageable)).willReturn(expected);

        assertThat(appointmentTypeService.search(searchRequestDTO, pageable), samePropertyValuesAs(expected));

        assertThat(appointmentTypeService.search(searchRequestDTO, pageable), hasSize(expected.size()));
    }

    @Test
    public void fetchAppointmentType_ShouldThrowException() {

        given(appointmentTypeRepository.fetchAppointmentTypeForDropdown())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        appointmentTypeService.fetchAppointmentTypeForDropdown();
    }

    @Test
    public void Should_Successfully_Return_AppointmentType() {
        given(appointmentTypeRepository.fetchAppointmentTypeForDropdown())
                .willReturn(fetchAppointmentTypeForDropDown());

        appointmentTypeService.fetchAppointmentTypeForDropdown();

        assertThat(appointmentTypeService.fetchAppointmentTypeForDropdown(),
                hasSize(fetchAppointmentTypeForDropDown().size()));

        assertThat(appointmentTypeService.fetchAppointmentTypeForDropdown(),
                samePropertyValuesAs(fetchAppointmentTypeForDropDown()));
    }

    @Test
    public void fetchAppointmentTypeDetails_ShouldThrowException() {
        Long id = 1L;

        given(appointmentTypeRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        appointmentTypeService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_AppointmentType_Details() {
        Long id = 1L;
        AppointmentTypeResponseDTO expected = fetchAppointmentTypeResponseDTO();

        given(appointmentTypeRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(appointmentTypeService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAppointmentTypeById_ShouldThrowException() {
        Long id = 1L;

        given(appointmentTypeRepository.fetchActiveAppointmentTypeById(id))
                .willThrow(NoContentFoundException.class);

        appointmentTypeService.fetchAppointmentTypeById(id);
    }

    @Test
    public void Should_Return_fetchAppointmentTypeById() {
        Long id = 1L;
        AppointmentType appointmentType = getAppointmentType();

        given(appointmentTypeRepository.fetchActiveAppointmentTypeById(id))
                .willReturn(Optional.of(appointmentType));

        assertThat(appointmentTypeService.fetchAppointmentTypeById(id), samePropertyValuesAs(appointmentType));
    }
}
