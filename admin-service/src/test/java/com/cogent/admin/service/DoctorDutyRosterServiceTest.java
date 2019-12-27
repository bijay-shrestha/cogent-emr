package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterSearchRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterUpdateRequestDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.DoctorDutyRosterDetailResponseDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.DoctorDutyRosterMinimalResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DoctorDutyRosterOverrideRepository;
import com.cogent.admin.repository.DoctorDutyRosterRepository;
import com.cogent.admin.repository.DoctorWeekDaysDutyRosterRepository;
import com.cogent.admin.service.impl.DoctorDutyRosterServiceImpl;
import com.cogent.admin.utils.DateUtils;
import com.cogent.persistence.model.DoctorDutyRoster;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.doctorDutyRoster.DoctorDutyRosterRequestUtils.*;
import static com.cogent.admin.dto.doctorDutyRoster.DoctorDutyRosterResponseUtils.*;
import static com.cogent.admin.dto.weekDays.WeekDaysResponseUtils.fetchWeekDays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author smriti on 26/11/2019
 */
public class DoctorDutyRosterServiceTest {

    @InjectMocks
    private DoctorDutyRosterServiceImpl doctorDutyRosterService;

    @Mock
    private DoctorDutyRosterRepository doctorDutyRosterRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private SpecializationService specializationService;

    @Mock
    private DoctorTypeService doctorTypeService;

    @Mock
    private WeekDaysService weekDaysService;

    @Mock
    private DoctorWeekDaysDutyRosterRepository doctorWeekDaysDutyRosterRepository;

    @Mock
    private DoctorDutyRosterOverrideRepository doctorDutyRosterOverrideRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save() {

        Should_Throw_Exception_When_DoctorDutyRoster_AlreadyExists();
        Should_Throw_Exception_When_DoctorNotFound();
        Should_Throw_Exception_When_SpecializationNotFound();
        Should_Throw_Exception_When_DoctorTypeNotFound();
        Should_Successfully_Save_DoctorDutyRoster();
        Should_ThrowException_When_WeekDaysNotFound();
        Should_Successfully_Save_DoctorWeekDaysRoster();
        Should_Successfully_Save_DoctorDutyRosterOverride();
    }

    @Test
    public void update() {
        Should_ThrowException_When_DoctorDutyRosterNotFound();
        Should_UpdateDoctorDutyRoster();
        Should_Update_DoctorWeekDaysRoster();
        Should_Update_DoctorDutyRosterOverride();
    }

    @Test
    public void delete() {
        delete_ShouldThrowException_When_DoctorDutyRosterNotFound();
        Should_Delete_DoctorDutyRoster();
    }

    @Test
    public void searchTest() {
        searchDoctorDutyRoster_ShouldThrowException();
        Should_Successfully_Return_DoctorDutyRosterList();
    }

    @Test
    public void Should_Throw_Exception_When_DoctorDutyRoster_AlreadyExists() {
        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        given(doctorDutyRosterRepository.validateDoctorDutyRosterCount(
                requestDTO.getDoctorId(),
                requestDTO.getSpecializationId(),
                requestDTO.getFromDate(),
                requestDTO.getToDate())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        doctorDutyRosterService.save(requestDTO);
    }

    private void validateDoctorDutyRosterCount(DoctorDutyRosterRequestDTO requestDTO) {
        given(doctorDutyRosterRepository.validateDoctorDutyRosterCount(
                requestDTO.getDoctorId(),
                requestDTO.getSpecializationId(),
                requestDTO.getFromDate(),
                requestDTO.getToDate())).willReturn(0L);
    }

    @Test
    public void Should_Throw_Exception_When_DoctorNotFound() {

        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        validateDoctorDutyRosterCount(requestDTO);

        given(doctorService.fetchDoctorById(requestDTO.getDoctorId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.save(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_SpecializationNotFound() {

        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        validateDoctorDutyRosterCount(requestDTO);

        given(specializationService.fetchActiveSpecializationById(requestDTO.getSpecializationId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.save(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_DoctorTypeNotFound() {

        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        validateDoctorDutyRosterCount(requestDTO);

        given(doctorTypeService.fetchDoctorTypeById(requestDTO.getDoctorTypeId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_DoctorDutyRoster() {
        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        validateDoctorDutyRosterCount(requestDTO);

        given(doctorDutyRosterRepository.save(any(DoctorDutyRoster.class))).willReturn(fetchDoctorDutyRoster());

        doctorDutyRosterService.save(requestDTO);

        verify(doctorDutyRosterRepository, times(1))
                .save(ArgumentMatchers.any(DoctorDutyRoster.class));
    }

    @Test
    public void Should_ThrowException_When_WeekDaysNotFound() {
        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        requestDTO.getDoctorWeekDaysDutyRosterRequestDTOS().forEach(
                weekDaysDutyRosterRequestDTO ->
                        given(weekDaysService.fetchWeekDaysById(weekDaysDutyRosterRequestDTO.getWeekDaysId()))
                                .willThrow(NoContentFoundException.class));

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_DoctorWeekDaysRoster() {

        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        requestDTO.getDoctorWeekDaysDutyRosterRequestDTOS().forEach(
                weekDaysDutyRosterRequestDTO ->
                        given(weekDaysService.fetchWeekDaysById(weekDaysDutyRosterRequestDTO.getWeekDaysId()))
                                .willReturn(fetchWeekDays()));

        doctorDutyRosterService.save(requestDTO);

        verify(doctorWeekDaysDutyRosterRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    public void Should_Successfully_Save_DoctorDutyRosterOverride() {

        DoctorDutyRosterRequestDTO requestDTO = getDoctorDutyRosterRequestDTO();

        doctorDutyRosterService.save(requestDTO);

        verify(doctorDutyRosterOverrideRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    public void Should_ThrowException_When_DoctorDutyRosterNotFound() {

        DoctorDutyRosterUpdateRequestDTO updateRequestDTO = getDoctorDutyRosterUpdateRequestDTO();

        given(doctorDutyRosterRepository.findDoctorDutyRosterById(updateRequestDTO.getDoctorDutyRosterId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.update(updateRequestDTO);
    }

    @Test
    public void Should_UpdateDoctorDutyRoster() {

        DoctorDutyRosterUpdateRequestDTO updateRequestDTO = getDoctorDutyRosterUpdateRequestDTO();

        given(doctorDutyRosterRepository.findDoctorDutyRosterById(updateRequestDTO.getDoctorDutyRosterId()))
                .willReturn(Optional.of(fetchDoctorDutyRoster()));

        doctorDutyRosterService.update(updateRequestDTO);
    }

    @Test
    public void Should_Update_DoctorWeekDaysRoster() {

        DoctorDutyRosterUpdateRequestDTO requestDTO = getDoctorDutyRosterUpdateRequestDTO();

        given(doctorDutyRosterRepository.findDoctorDutyRosterById(requestDTO.getDoctorDutyRosterId()))
                .willReturn(Optional.of(fetchDoctorDutyRoster()));

        requestDTO.getWeekDaysDutyRosterUpdateRequestDTOS().forEach(
                weekDaysDutyRosterRequestDTO -> given(
                        weekDaysService.fetchWeekDaysById(
                                weekDaysDutyRosterRequestDTO.getWeekDaysId()))
                        .willReturn(fetchWeekDays()));

        doctorDutyRosterService.update(requestDTO);

        verify(doctorWeekDaysDutyRosterRepository, times(1))
                .saveAll(anyIterable());
    }

    @Test
    public void Should_Update_DoctorDutyRosterOverride() {

        DoctorDutyRosterUpdateRequestDTO requestDTO = getDoctorDutyRosterUpdateRequestDTO();

        given(doctorDutyRosterRepository.findDoctorDutyRosterById(requestDTO.getDoctorDutyRosterId()))
                .willReturn(Optional.of(fetchDoctorDutyRoster()));

        doctorDutyRosterService.update(requestDTO);

        verify(doctorDutyRosterOverrideRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    public void delete_ShouldThrowException_When_DoctorDutyRosterNotFound() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(doctorDutyRosterRepository.findDoctorDutyRosterById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_Delete_DoctorDutyRoster() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(doctorDutyRosterRepository.findDoctorDutyRosterById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(fetchDoctorDutyRoster()));

        doctorDutyRosterService.delete(deleteRequestDTO);
    }

    @Test
    public void searchDoctorDutyRoster_ShouldThrowException() {

        Pageable pageable = PageRequest.of(1, 10);
        DoctorDutyRosterSearchRequestDTO searchRequestDTO = getDoctorDutyRosterSearchRequestDTO();

        given(doctorDutyRosterRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        doctorDutyRosterService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_DoctorDutyRosterList() {

        DoctorDutyRosterSearchRequestDTO searchRequestDTO = getDoctorDutyRosterSearchRequestDTO();
        List<DoctorDutyRosterMinimalResponseDTO> expected = fetchDoctorDutyRosterMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(doctorDutyRosterRepository.search(searchRequestDTO, pageable)).willReturn(expected);

        assertThat(doctorDutyRosterService.search(searchRequestDTO, pageable), samePropertyValuesAs(expected));

        assertThat(doctorDutyRosterService.search(searchRequestDTO, pageable), hasSize(expected.size()));
    }

    @Test
    public void detailsTest() {
        fetchDoctorDutyRosterDetails_ShouldThrowException();
        Should_Return_Doctor_Duty_Roster_Details();
    }

    @Test
    public void fetchDoctorDutyRosterDetails_ShouldThrowException() {
        Long id = 1L;

        given(doctorDutyRosterRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorDutyRosterService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Doctor_Duty_Roster_Details() {
        Long id = 1L;
        DoctorDutyRosterDetailResponseDTO expected = fetchDoctorDutyRosterDetailResponseDTO();

        given(doctorDutyRosterRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(doctorDutyRosterService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test
    public void isDateBetweenInclusive() {

        java.sql.Date fromDate = java.sql.Date.valueOf("2019-12-17");
        java.sql.Date toDate = java.sql.Date.valueOf("2019-12-29");

        java.sql.Date target = java.sql.Date.valueOf("2019-12-17");
        java.sql.Date target1 = java.sql.Date.valueOf("2019-12-29");

        boolean isInclusive = DateUtils.isDateBetweenInclusive(fromDate, toDate, target) &&
                DateUtils.isDateBetweenInclusive(fromDate, toDate, target1);

        if (!isInclusive)
            System.out.println("error");
    }

}
