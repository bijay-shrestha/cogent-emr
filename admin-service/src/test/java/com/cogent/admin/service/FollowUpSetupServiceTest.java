package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupMinimalResponseDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.FollowUpSetupRepository;
import com.cogent.admin.service.impl.FollowUpSetupServiceImpl;
import com.cogent.persistence.model.FollowUpSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.followUpSetup.FollowUpSetupRequestUtils.getFollowUpSetupRequestDTO;
import static com.cogent.admin.dto.followUpSetup.FollowUpSetupRequestUtils.getFollowUpSetupUpdateRequestDTO;
import static com.cogent.admin.dto.followUpSetup.FollowUpSetupResponseUtils.*;
import static com.cogent.admin.dto.request.patientType.PatientTypeResponseUtils.getPatientType;
import static com.cogent.admin.utils.FollowUpSetupUtils.convertDTOToFollowUpSetup;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-11-04
 */
public class FollowUpSetupServiceTest {

    @InjectMocks
    private FollowUpSetupServiceImpl followUpSetupService;

    @Mock
    private FollowUpSetupRepository followUpSetupRepository;

    @Mock
    private PatientTypeService patientTypeService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void save() {
        ShouldThrowException_When_PatientTypeNotFound();
        Should_Save_FollowUpSetup();
    }

    @Test
    public void update() {
        ShouldThrowException_When_FollowUpSetupNotFound();
        update_ShouldThrowException_When_PatientTypeNotFound();
        Should_Update_FollowUpSetup();
    }

    @Test
    public void deleteTest() {
        deleteFollowUpSetup_ShouldThrowException();
        Should_Delete_FollowUpSetup();
    }

    @Test
    public void searchTest() {
        fetchFollowUpSetup_ShouldThrowException();
        Should_Successfully_Return_FollowUpSetup();
    }

    @Test
    public void detailsTest() {
        fetchFollowUpSetupDetails_ShouldThrowException();
        Should_Return_FollowUpSetupDetails();
    }

    @Test(expected = NoContentFoundException.class)
    public void ShouldThrowException_When_PatientTypeNotFound() {
        FollowUpSetupRequestDTO requestDTO = getFollowUpSetupRequestDTO();

        given(patientTypeService.fetchPatientTypeById(requestDTO.getPatientTypeId()))
                .willThrow(NoContentFoundException.class);

        followUpSetupService.save(requestDTO);
    }

    @Test
    public void Should_Save_FollowUpSetup() {
        FollowUpSetupRequestDTO requestDTO = getFollowUpSetupRequestDTO();
        FollowUpSetup expected = convertDTOToFollowUpSetup(requestDTO, getPatientType());

        given(patientTypeService.fetchPatientTypeById(requestDTO.getPatientTypeId()))
                .willReturn(getPatientType());

        followUpSetupService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "patientType"})
                .matches(getFollowUpSetup()));

        Assert.assertTrue(new ReflectionEquals(expected.getPatientType().getId())
                .matches(getFollowUpSetup().getPatientType().getId()));

        verify(followUpSetupRepository, times(1))
                .save(any(FollowUpSetup.class));
    }

    @Test
    public void ShouldThrowException_When_FollowUpSetupNotFound() {
        FollowUpSetupUpdateRequestDTO requestDTO = getFollowUpSetupUpdateRequestDTO();

        given(followUpSetupRepository.findFollowUpSetupById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        followUpSetupService.update(requestDTO);
    }

    @Test(expected = NoContentFoundException.class)
    public void update_ShouldThrowException_When_PatientTypeNotFound() {
        FollowUpSetupUpdateRequestDTO requestDTO = getFollowUpSetupUpdateRequestDTO();

        given(followUpSetupRepository.findFollowUpSetupById(requestDTO.getId()))
                .willReturn(Optional.of(getFollowUpSetup()));

        given(patientTypeService.fetchPatientTypeById(requestDTO.getPatientTypeId()))
                .willThrow(NoContentFoundException.class);

        followUpSetupService.update(requestDTO);
    }

    @Test
    public void Should_Update_FollowUpSetup() {
        FollowUpSetupUpdateRequestDTO requestDTO = getFollowUpSetupUpdateRequestDTO();

        given(followUpSetupRepository.findFollowUpSetupById(requestDTO.getId()))
                .willReturn(Optional.of(getFollowUpSetup()));

        given(patientTypeService.fetchPatientTypeById(requestDTO.getPatientTypeId()))
                .willReturn(getPatientType());

        followUpSetupService.update(requestDTO);
    }

    @Test
    public void deleteFollowUpSetup_ShouldThrowException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(followUpSetupRepository.findFollowUpSetupById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        followUpSetupService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_Delete_FollowUpSetup() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(followUpSetupRepository.findFollowUpSetupById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(getFollowUpSetup()));

        followUpSetupService.delete(deleteRequestDTO);
    }

    @Test
    public void fetchFollowUpSetup_ShouldThrowException() {

        given(followUpSetupRepository.fetchFollowUpSetup())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        followUpSetupService.fetchFollowUpSetup();
    }

    @Test
    public void Should_Successfully_Return_FollowUpSetup() {
        List<FollowUpSetupMinimalResponseDTO> expected = getFollowUpSetupMinimalResponseDTOS();

        given(followUpSetupRepository.fetchFollowUpSetup()).willReturn(expected);

        assertThat(followUpSetupService.fetchFollowUpSetup(), samePropertyValuesAs(expected));

        assertThat(followUpSetupService.fetchFollowUpSetup(), hasSize(expected.size()));
    }

    @Test
    public void fetchFollowUpSetupDetails_ShouldThrowException() {
        Long id = 1L;

        given(followUpSetupRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        followUpSetupService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_FollowUpSetupDetails() {
        Long id = 1L;
        FollowUpSetupResponseDTO expected = getFollowUpSetupResponseDTO();

        given(followUpSetupRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(followUpSetupService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }
}
