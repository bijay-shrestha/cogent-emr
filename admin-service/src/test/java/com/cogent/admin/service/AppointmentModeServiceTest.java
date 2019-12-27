package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AppointmentModeRepository;
import com.cogent.admin.service.impl.AppointmentModeServiceImpl;
import com.cogent.persistence.model.AppointmentMode;
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
import static com.cogent.admin.dto.appointmentMode.AppointmentModeRequestUtils.*;
import static com.cogent.admin.dto.appointmentMode.AppointmentModeResponseUtils.*;
import static com.cogent.admin.utils.AppointmentModeUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author smriti on 2019-10-10
 */
public class AppointmentModeServiceTest {

    @InjectMocks
    private AppointmentModeServiceImpl appointmentModeService;

    @Mock
    private AppointmentModeRepository appointmentModeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();

        Should_Successfully_Save_AppointmentMode();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_AppointmentMode_NotFound();

        Should_Throw_Exception_When_Name_Already_Exists();

        Should_Successfully_Update_AppointmentMode();
    }

    @Test
    public void deleteTest() {

        deleteAppointmentMode_ShouldThrowException();

        Should_SuccessFully_Delete_AppointmentMode();
    }

    @Test
    public void searchTest() {

        searchAppointmentMode_ShouldThrowException();

        Should_Successfully_Return_AppointmentModeList();
    }

    @Test
    public void dropdownTest() {
        fetchAppointmentMode_ShouldThrowException();

        Should_Successfully_Return_AppointmentMode();
    }

    @Test
    public void detailsTest() {
        fetchAppointmentModeDetails_ShouldThrowException();

        Should_Return_AppointmentMode_Details();
    }

    @Test
    public void fetchActiveAppointmentMode() {
        fetchActiveAppointmentMode_ShouldThrowException();
        Should_Return_ActiveAppointmentMode();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        AppointmentModeRequestDTO requestDTO = getAppointmentModeRequestDTO();

        given(appointmentModeRepository.fetchAppointmentModeByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        appointmentModeService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_AppointmentMode() {
        AppointmentModeRequestDTO requestDTO = getAppointmentModeRequestDTO();

        AppointmentMode expected = convertDTOToAppointmentMode(requestDTO);

        given(appointmentModeRepository.fetchAppointmentModeByName(requestDTO.getName()))
                .willReturn(0L);

        appointmentModeService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getAppointmentMode()));

        verify(appointmentModeRepository, times(1))
                .save(ArgumentMatchers.any(AppointmentMode.class));
    }

    @Test
    public void Should_Throw_Exception_When_AppointmentMode_NotFound() {
        AppointmentModeUpdateRequestDTO requestDTO = getAppointmentModeUpdateRequestDTO();

        given(appointmentModeRepository.findAppointmentModeById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        appointmentModeService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        AppointmentModeUpdateRequestDTO requestDTO = getAppointmentModeUpdateRequestDTO();

        given(appointmentModeRepository.findAppointmentModeById(requestDTO.getId()))
                .willReturn(Optional.of(getAppointmentMode()));

        given(appointmentModeRepository.fetchAppointmentModeByIdAndName(
                requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        appointmentModeService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_AppointmentMode() {

        AppointmentModeUpdateRequestDTO requestDTO = getAppointmentModeUpdateRequestDTO();
        AppointmentMode expected = convertToUpdatedAppointmentMode(
                requestDTO, getAppointmentMode());

        given(appointmentModeRepository.findAppointmentModeById(requestDTO.getId()))
                .willReturn(Optional.of(getAppointmentMode()));

        given(appointmentModeRepository.fetchAppointmentModeByIdAndName(requestDTO.getId(),
                requestDTO.getName())).willReturn(0L);

        appointmentModeService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"})
                .matches(getUpdatedAppointmentMode()));
    }

    @Test
    public void deleteAppointmentMode_ShouldThrowException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(appointmentModeRepository.findAppointmentModeById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        appointmentModeService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_AppointmentMode() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        AppointmentMode appointmentMode = getAppointmentMode();

        given(appointmentModeRepository.findAppointmentModeById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(appointmentMode));

        AppointmentMode expected = convertToDeletedAppointmentMode(appointmentMode, deleteRequestDTO);

        given(appointmentModeRepository.save(expected)).willReturn(getDeletedAppointmentMode());

        appointmentModeService.delete(deleteRequestDTO);

        assertTrue(appointmentModeRepository.save(expected).getStatus().equals('D') &&
                appointmentModeRepository.save(expected).getRemarks().equals(getDeletedAppointmentMode().getRemarks()));
    }

    @Test
    public void searchAppointmentMode_ShouldThrowException() {
        Pageable pageable = PageRequest.of(1, 10);
        AppointmentModeSearchRequestDTO searchRequestDTO = getAppointmentModeSearchRequestDTO();

        given(appointmentModeRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        appointmentModeService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_AppointmentModeList() {
        AppointmentModeSearchRequestDTO searchRequestDTO = getAppointmentModeSearchRequestDTO();
        List<AppointmentModeMinimalResponseDTO> expected = fetchAppointmentModeMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(appointmentModeRepository.search(searchRequestDTO, pageable)).willReturn(expected);

        assertThat(appointmentModeService.search(searchRequestDTO, pageable), samePropertyValuesAs(expected));

        assertThat(appointmentModeService.search(searchRequestDTO, pageable), hasSize(expected.size()));
    }

    @Test
    public void fetchAppointmentMode_ShouldThrowException() {

        given(appointmentModeRepository.fetchActiveAppointmentModeForDropdown())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        appointmentModeService.fetchAppointmentModeForDropdown();
    }

    @Test
    public void Should_Successfully_Return_AppointmentMode() {
        given(appointmentModeRepository.fetchActiveAppointmentModeForDropdown())
                .willReturn(fetchAppointmentModeForDropDown());

        appointmentModeService.fetchAppointmentModeForDropdown();

        assertThat(appointmentModeService.fetchAppointmentModeForDropdown(),
                hasSize(fetchAppointmentModeForDropDown().size()));

        assertThat(appointmentModeService.fetchAppointmentModeForDropdown(),
                samePropertyValuesAs(fetchAppointmentModeForDropDown()));
    }

    @Test
    public void fetchAppointmentModeDetails_ShouldThrowException() {
        Long id = 1L;

        given(appointmentModeRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        appointmentModeService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_AppointmentMode_Details() {
        Long id = 1L;
        AppointmentModeResponseDTO expected = fetchAppointmentModeResponseDTO();

        given(appointmentModeRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(appointmentModeService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchActiveAppointmentMode_ShouldThrowException() {
        Long id = 1L;

        given(appointmentModeRepository.fetchActiveAppointmentModeById(id))
                .willThrow(NoContentFoundException.class);

        appointmentModeService.fetchAppointmentModeById(id);
    }

    @Test
    public void Should_Return_ActiveAppointmentMode() {
        Long id = 1L;
        AppointmentMode appointmentMode = getAppointmentMode();

        given(appointmentModeRepository.fetchActiveAppointmentModeById(id))
                .willReturn(Optional.of(appointmentMode));

        assertThat(appointmentModeService.fetchAppointmentModeById(id), samePropertyValuesAs(appointmentMode));
    }
}
