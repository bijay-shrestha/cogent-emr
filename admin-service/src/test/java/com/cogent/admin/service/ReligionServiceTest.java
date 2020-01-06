package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.religion.ReligionResponseUtils;
import com.cogent.admin.dto.request.religion.ReligionRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionUpdateRequestDTO;
import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import com.cogent.admin.dto.surname.SurnameResponseUtils;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ReligionRepository;
import com.cogent.admin.service.impl.ReligionServiceImpl;
import com.cogent.admin.utils.ReligionUtils;
import com.cogent.persistence.model.Religion;
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
import static com.cogent.admin.dto.religion.ReligionRequestUtils.*;
import static com.cogent.admin.dto.religion.ReligionResponseUtils.*;
import static com.cogent.admin.utils.ReligionUtils.convertDTOToReligion;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ReligionServiceTest {
    @InjectMocks
    private ReligionServiceImpl religionService;

    @Mock
    private ReligionRepository religionRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();
        Should_Successfully_Save_Religion();

    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_Religion_NotFound();
        Should_Throw_Exception_When_Name_Already_Exists();
    }

    @Test
    public void deleteReligion() {

        Should_Throw_Exception_When_Religion_Is_Null();
        Should_SuccessFully_Delete_Religion();

    }

    @Test
    public void searchReligion() {
        Should_Throw_Exception_When_ReligionList_Empty();
        Should_Successfully_Return_ReligionList();
    }

    @Test
    public void fetchReligionForDropDown() {
        Should_ThrowException_When_No_ReligionFound();
        Should_Return_Religions();
    }

    @Test
    public void Should_SuccessFully_Delete_Religion() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Religion religion = getReligion();

        given(religionRepository.findReligionById(deleteRequestDTO.getId())).willReturn(Optional.of(religion));

        Religion expected = ReligionUtils.convertToDeletedReligion(religion, deleteRequestDTO);

        given(religionRepository.save(expected)).willReturn(getDeletedReligion());

    }

    @Test
    public void Should_Throw_Exception_When_Religion_Is_Null() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(religionRepository.findReligionById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        religionService.delete(deleteRequestDTO);
    }


    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        ReligionRequestDTO requestDTO = getReligionRequestDTO();

        given(religionRepository.fetchReligionByName(requestDTO.getName()))
                .willReturn(1l);

        thrown.expect(DataDuplicationException.class);

        religionService.save(requestDTO);

    }

    @Test
    public void Should_Successfully_Save_Religion() {

        ReligionRequestDTO requestDTO = getReligionRequestDTO();

        Religion expected = convertDTOToReligion(requestDTO);

        given(religionRepository.fetchReligionByName(requestDTO.getName()))
                .willReturn(0l);

        religionService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getReligion()));

    }

    @Test
    public void Should_Throw_Exception_When_Religion_NotFound() {
        ReligionUpdateRequestDTO requestDTO = getReligionUpdateRequestDTO();

        given(religionRepository.findReligionById(requestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        religionService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        ReligionUpdateRequestDTO requestDTO = getReligionUpdateRequestDTO();

        given(religionRepository.findReligionById(requestDTO.getId())).willReturn(Optional.of(getReligion()));

        given(religionRepository.findReligionByIdAndName(requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        religionService.update(requestDTO);

    }

    @Test
    public void Should_Throw_Exception_When_ReligionList_Empty() {
        Pageable pageable = PageRequest.of(1, 10);

        ReligionSearchRequestDTO searchRequestDTO = getReligionSearchRequestDTO();
        given(religionRepository.search(searchRequestDTO, pageable)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        religionService.search(searchRequestDTO, pageable);

    }

    @Test
    public void Should_Successfully_Return_ReligionList() {

        ReligionSearchRequestDTO searchRequestDTO = getReligionSearchRequestDTO();

        List<ReligionResponseDTO> expected = fetchReligionResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(religionRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(religionService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(religionService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));


    }

    @Test
    public void Should_ThrowException_When_No_ReligionFound() {

        given(religionRepository.fetchReligionForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        religionService.fetchReligionForDropDown();

    }

    @Test
    public void Should_Return_Religions() {
        given(religionRepository.fetchReligionForDropDown())
                .willReturn(ReligionResponseUtils.fetchReligionForDropDown());

        religionService.fetchReligionForDropDown();

        assertThat(religionService.fetchReligionForDropDown(),
                hasSize(ReligionResponseUtils.fetchReligionForDropDown().size()));

        assertThat(religionService.fetchReligionForDropDown(),
                samePropertyValuesAs(SurnameResponseUtils.fetchSurnameForDropDown()));
    }

    @Test
    public void fetchReligion_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(religionRepository.findReligionById(1L))
                .willReturn(Optional.empty());

        religionService.fetchReligion(1L);

    }

    @Test
    public void fetchReligion_ShouldReturnData() {

        given(religionRepository.findReligionById(1L))
                .willReturn(Optional.of((getReligion())));

        religionService.fetchReligion(1L);

        verify(religionRepository).findReligionById(1L);

    }


}
