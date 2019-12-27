package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import com.cogent.admin.dto.maritalStatus.MaritalStatusResponseUtils;
import com.cogent.admin.dto.surname.SurnameResponseUtils;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.MaritalStatusRepository;
import com.cogent.admin.service.impl.MaritalStatusServiceImpl;
import com.cogent.admin.utils.MaritalStatusUtils;
import com.cogent.persistence.model.MaritalStatus;
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
import static com.cogent.admin.dto.maritalStatus.MaritalStatusResponseUtils.*;
import static com.cogent.admin.dto.maritalStatus.MaritalStatusTestUtils.*;
import static com.cogent.admin.utils.MaritalStatusUtils.convertDTOToMaritalStatus;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MaritalStatusTest {

    @InjectMocks
    private MaritalStatusServiceImpl maritalStatusService;

    @Mock
    private MaritalStatusRepository maritalStatusRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();
        Should_Successfully_Save_MaritalStatus();

    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_MaritalStatus_NotFound();
        Should_Throw_Exception_When_Name_Already_Exists();
    }

    @Test
    public void deleteMaritalStatus() {

        Should_Throw_Exception_When_MaritalStatus_Is_Null();
        Should_SuccessFully_Delete_MaritalStatus();

    }

    @Test
    public void searchMaritalStatus() {
        Should_Throw_Exception_When_MaritalStatusList_Empty();
        Should_Successfully_Return_MaritalStatusList();
    }

    @Test
    public void fetchMaritalStatusForDropDown() {
        Should_ThrowException_When_No_MaritalStatusFound();
        Should_Return_MaritalStatus();
    }


    @Test
    public void Should_SuccessFully_Delete_MaritalStatus() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        MaritalStatus maritalStatus = getMaritalStatus();

        given(maritalStatusRepository.findMaritalStatusById(deleteRequestDTO.getId())).willReturn(Optional.of(maritalStatus));

        MaritalStatus expected = MaritalStatusUtils.convertToDeletedMaritalStatus(maritalStatus, deleteRequestDTO);

        given(maritalStatusRepository.save(expected)).willReturn(getDeletedMaritalStatus());

    }

    @Test
    public void Should_Throw_Exception_When_MaritalStatus_Is_Null() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(maritalStatusRepository.findMaritalStatusById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        maritalStatusService.delete(deleteRequestDTO);
    }


    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        MaritalStatusRequestDTO requestDTO = getMaritalStatusRequestDTO();

        given(maritalStatusRepository.fetchMaritalStatusByName(requestDTO.getName()))
                .willReturn(1l);

        thrown.expect(DataDuplicationException.class);

        maritalStatusService.save(requestDTO);

    }

    @Test
    public void Should_Successfully_Save_MaritalStatus() {

        MaritalStatusRequestDTO requestDTO = getMaritalStatusRequestDTO();

        MaritalStatus expected = convertDTOToMaritalStatus(requestDTO);

        given(maritalStatusRepository.fetchMaritalStatusByName(requestDTO.getName()))
                .willReturn(0l);

        maritalStatusService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getMaritalStatus()));

    }

    @Test
    public void Should_Throw_Exception_When_MaritalStatus_NotFound() {
        MaritalStatusUpdateRequestDTO requestDTO = getMaritalStatusUpdateRequestDTO();

        given(maritalStatusRepository.findMaritalStatusById(requestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        maritalStatusService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        MaritalStatusUpdateRequestDTO requestDTO = getMaritalStatusUpdateRequestDTO();

        given(maritalStatusRepository.findMaritalStatusById(requestDTO.getId())).willReturn(Optional.of(getMaritalStatus()));

        given(maritalStatusRepository.findMaritalStatusByIdAndName(requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        maritalStatusService.update(requestDTO);

    }

    @Test
    public void Should_Throw_Exception_When_MaritalStatusList_Empty() {
        Pageable pageable = PageRequest.of(1, 10);

        MaritalStatusSearchRequestDTO searchRequestDTO = getMaritalStatusSearchRequestDTO();
        given(maritalStatusRepository.search(searchRequestDTO, pageable)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        maritalStatusService.search(searchRequestDTO, pageable);

    }

    @Test
    public void Should_Successfully_Return_MaritalStatusList() {

        MaritalStatusSearchRequestDTO searchRequestDTO = getMaritalStatusSearchRequestDTO();

        List<MaritalStatusResponseDTO> expected = fetchMaritalStatusResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(maritalStatusRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(maritalStatusService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(maritalStatusService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));


    }

    @Test
    public void Should_ThrowException_When_No_MaritalStatusFound() {

        given(maritalStatusRepository.fetchMaritalStatusForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        maritalStatusService.fetchMaritalStatusForDropDown();

    }

    @Test
    public void Should_Return_MaritalStatus() {
        given(maritalStatusRepository.fetchMaritalStatusForDropDown())
                .willReturn(MaritalStatusResponseUtils.fetchMaritalStatusForDropDown());

        maritalStatusService.fetchMaritalStatusForDropDown();

        assertThat(maritalStatusService.fetchMaritalStatusForDropDown(),
                hasSize(MaritalStatusResponseUtils.fetchMaritalStatusForDropDown().size()));

        assertThat(maritalStatusService.fetchMaritalStatusForDropDown(),
                samePropertyValuesAs(SurnameResponseUtils.fetchSurnameForDropDown()));
    }

    @Test
    public void fetchMaritalStatus_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(maritalStatusRepository.findMaritalStatusById(1L))
                .willReturn(Optional.empty());

        maritalStatusService.fetchMaritalStatus(1L);

    }

    @Test
    public void fetchMaritalStatus_ShouldReturnData() {

        given(maritalStatusRepository.findMaritalStatusById(1L))
                .willReturn(Optional.of((getMaritalStatus())));

        maritalStatusService.fetchMaritalStatus(1L);


        verify(maritalStatusRepository).findMaritalStatusById(1L);

    }

}
