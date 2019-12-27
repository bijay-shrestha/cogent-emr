package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.request.drug.DrugUpdateRequestDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DrugRepository;
import com.cogent.admin.service.impl.DrugServiceImpl;
import com.cogent.persistence.model.Drug;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.drug.DrugTestUtils.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DrugUtils.convertToDrug;
import static com.cogent.admin.utils.DrugUtils.updateDrug;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DrugServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    DrugRepository drugRepository;

    @InjectMocks
    DrugServiceImpl drugService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createDrug_ShouldReturnNameAndCodeExists();
        createDrug_ShouldReturnNameExists();
        createDrug_ShouldReturnCodeExists();
        createDrug_ShouldCreateDrug();
    }

    @Test
    public void dropDownList() {
        dropDownList_ShouldReturnNoContentFound();
        dropDownList_ShouldData();
    }

    @Test
    public void activeDropDownList() {
        activeDropDownList_ShouldReturnNoContentFound();
        activeDropDownList_ShouldData();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchDrugDetails_ShouldReturnNoContentFound();
        fetchDrugDetails_ShouldReturnData();
    }

    @Test
    public void delete() {
        deleteDrug_ShouldReturnNoContentFound();
        deleteDrug_ShouldDelete();
    }

    @Test
    public void update() {
        updateDrug_ShouldReturnNoContentFound();
        updateDrug_ShouldReturnNameAlreadyExists();
        updateDrug_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchDrug_ShouldReturnNoContentFound();
        searchDrug_ShouldReturnDrug();
    }

    @Test
    public void createDrug_ShouldReturnNameAndCodeExists() {

        thrown.expect(DataDuplicationException.class);

        DrugRequestDTO drugRequestDTO = getDrugRequestDTO();

        given(drugRepository.fetchDrugByNameOrCode(drugRequestDTO.getName()
                , drugRequestDTO.getCode()))
                .willReturn(getDrugObjectForNameAndCode());

        drugService.createDrug(drugRequestDTO);

        verify(drugRepository)
                .fetchDrugByNameOrCode(drugRequestDTO.getName(), drugRequestDTO.getCode());
    }

    @Test
    public void createDrug_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        DrugRequestDTO drugRequestDTO = getDrugRequestDTO();

        given(drugRepository.fetchDrugByNameOrCode(drugRequestDTO.getName()
                , drugRequestDTO.getCode()))
                .willReturn(getDrugObjectForName());

        drugService.createDrug(drugRequestDTO);

        verify(drugRepository)
                .fetchDrugByNameOrCode(drugRequestDTO.getName(), drugRequestDTO.getCode());
    }

    @Test
    public void createDrug_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        DrugRequestDTO drugRequestDTO = getDrugRequestDTO();

        given(drugRepository.fetchDrugByNameOrCode(drugRequestDTO.getName()
                , drugRequestDTO.getCode()))
                .willReturn(getDrugObjectForCode());

        drugService.createDrug(drugRequestDTO);

        verify(drugRepository)
                .fetchDrugByNameOrCode(drugRequestDTO.getName(), drugRequestDTO.getCode());
    }

    @Test
    public void createDrug_ShouldCreateDrug() {

        DrugRequestDTO drugRequestDTO = getDrugRequestDTO();

        Drug expected = convertToDrug(drugRequestDTO);

        given(drugRepository.fetchDrugByNameOrCode(drugRequestDTO.getName()
                , drugRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        drugService.createDrug(drugRequestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getDrugInfo()));

        verify(drugRepository)
                .save(any(Drug.class));
    }

    @Test
    public void deleteDrug_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(drugRepository.fetchDrugById(1L))
                .willReturn(Optional.empty());

        drugService.deleteDrug(getDeleteRequestDTO());

        verify(drugRepository)
                .fetchDrugById(1L);

    }

    @Test
    public void deleteDrug_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Drug expected = updateDrug
                .apply(getDrugInfo(), deleteRequestDTO);

        given(drugRepository.fetchDrugById(1L))
                .willReturn(Optional.of(getDrugInfo()));

        drugService.deleteDrug(deleteRequestDTO);

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDrugInfoToDelete()));

        verify(drugRepository)
                .fetchDrugById(1L);

    }

    @Test
    public void updateDrug_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(drugRepository.fetchDrugById(1L))
                .willReturn(Optional.empty());

        drugService.updateDrug(getDrugUpdateRequestDTO());

        verify(drugRepository)
                .fetchDrugById(1L);
    }

    @Test
    public void updateDrug_ShouldReturnNameAlreadyExists() {

        DrugUpdateRequestDTO requestDTO = getDrugUpdateRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(drugRepository.fetchDrugById(requestDTO.getId()))
                .willReturn(Optional.of(getDrugInfo()));

        given(drugRepository.checkIfDrugNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getDrugObjectForNameAndCode());

        drugService.updateDrug(requestDTO);

        verify(drugRepository)
                .fetchDrugById(requestDTO.getId());

        verify(drugRepository)
                .checkIfDrugNameAndCodeExists(requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void updateDrug_ShouldUpdate() {

        DrugUpdateRequestDTO requestDTO = getDrugUpdateRequestDTO();

        Drug expected = updateDrug.apply(getDrugInfo(),
                ConvertToDeleteRequestDTO.apply(requestDTO.getRemarks(),
                        requestDTO.getStatus()));

        given(drugRepository.fetchDrugById(requestDTO.getId()))
                .willReturn(Optional.of(getDrugInfo()));

        given(drugRepository.checkIfDrugNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        drugService.updateDrug(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDrugInfoToUpdate()));

        verify(drugRepository)
                .fetchDrugById(requestDTO.getId());

        verify(drugRepository)
                .checkIfDrugNameAndCodeExists(requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());

    }

    @Test(expected = NoContentFoundException.class)

    public void searchDrug_ShouldReturnNoContentFound() {

        DrugSearchRequestDTO drugSearchRequestDTO = getSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(drugRepository.searchDrug(drugSearchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Drug.class));

        drugService.searchDrug(drugSearchRequestDTO, pageable);

        verify(drugRepository)
                .searchDrug(drugSearchRequestDTO, pageable);
    }

    @Test
    public void searchDrug_ShouldReturnDrug() {

        DrugSearchRequestDTO drugSearchRequestDTO = getSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(drugRepository.searchDrug(drugSearchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        drugService.searchDrug(drugSearchRequestDTO, pageable);

        verify(drugRepository)
                .searchDrug(drugSearchRequestDTO, pageable);
    }


    @Test(expected = NoContentFoundException.class)
    public void fetchDrugDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(drugRepository.fetchDrugDetails(id))
                .willThrow(new NoContentFoundException(Drug.class, "id", id.toString()));

        drugService.fetchDrugDetails(1L);

        verify(drugRepository)
                .fetchDrugDetails(id);

    }

    @Test
    public void fetchDrugDetails_ShouldReturnData() {
        DrugResponseDTO drugResponseDTO = getDrugResponseDTO();

        given(drugRepository.fetchDrugDetails(1L))
                .willReturn((drugResponseDTO));

        drugService.fetchDrugDetails(1L);

        verify(drugRepository)
                .fetchDrugDetails(1L);

    }


    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(drugRepository.fetchDropDownList())
                .willReturn(Optional.empty());

        drugService.drugDropdown();

        verify(drugRepository)
                .fetchDropDownList();

    }

    @Test
    public void dropDownList_ShouldData() {
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(drugRepository.fetchDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        drugService.drugDropdown();

        verify(drugRepository)
                .fetchDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(drugRepository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        drugService.activeDrugDropdown();

        verify(drugRepository)
                .fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldData() {
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(drugRepository.fetchActiveDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        drugService.activeDrugDropdown();

        verify(drugRepository)
                .fetchActiveDropDownList();
    }


}
