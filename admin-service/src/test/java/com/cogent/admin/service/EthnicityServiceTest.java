package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.ethnicity.EthnicityTestUtils;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityUpdateRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.EthnicityRepository;
import com.cogent.admin.service.impl.EthnicityServiceImpl;
import com.cogent.persistence.model.Ethnicity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.ethnicity.EthnicityTestUtils.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.EthnicityUtils.convertToEthnicityInfo;
import static com.cogent.admin.utils.EthnicityUtils.updateEthnicity;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi  on 2019-08-25
 */

public class EthnicityServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    EthnicityRepository ethnicityRepository;

    @InjectMocks
    EthnicityServiceImpl ethnicityService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createEnthicity_ShouldReturnNameAndCodeExists();
        createEnthicity_ShouldReturnNameExists();
        createEnthicity_ShouldReturnCodeExists();
        createEnthicity_ShouldCreateEthnicity();
    }

    @Test
    public void delete() {
        deleteEthnicity_ShouldReturnNoContentFound();
        deleteEthnicity_ShouldDelete();
    }

    @Test
    public void update() {
        updateEthnicity_ShouldReturnNoContentFound();
        updateEthnicity_ShouldReturnNameAlreadyExists();
        updateEthnicity_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchEthnicity_ShouldReturnNoContentFound();
        searchEthnicity_ShouldEthnicity();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchEthnicityDetails_ShouldReturnNoContentFound();
        fetchEthnicityDetails_ShouldReturnData();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchEthnicityById() {
        fetchEthnicity_ShouldReturnNoContentFound();
        fetchEthnicity_ShouldReturnData();
    }

    @Test
    public void dropDown() {
        dropDownList_ShouldReturnNoContentFound();
        dropDownList_ShouldReturnData();
    }

    @Test
    public void activeDropDown() {
        activeDropDownList_ShouldReturnNoContentFound();
        activeDropDownList_ShouldReturnData();
    }


    @Test
    public void createEnthicity_ShouldReturnNameAndCodeExists() {
        thrown.expect(DataDuplicationException.class);

        EthnicityRequestDTO ethnicityRequestDTO = getEthnicityRequestDTO();

        given(ethnicityRepository.fetchEthnicityByNameOrCode(ethnicityRequestDTO.getName()
                , ethnicityRequestDTO.getCode()))
                .willReturn(EthnicityTestUtils.getEthnicityObjectForNameAndCode());

        ethnicityService.createEthnicity(ethnicityRequestDTO);
    }

    @Test
    public void createEnthicity_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        EthnicityRequestDTO ethnicityRequestDTO = getEthnicityRequestDTO();

        given(ethnicityRepository.fetchEthnicityByNameOrCode(ethnicityRequestDTO.getName()
                , ethnicityRequestDTO.getCode()))
                .willReturn(EthnicityTestUtils.getEthnicityObjectForName());

        ethnicityService.createEthnicity(ethnicityRequestDTO);
    }

    @Test
    public void createEnthicity_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        EthnicityRequestDTO ethnicityRequestDTO = getEthnicityRequestDTO();

        given(ethnicityRepository.fetchEthnicityByNameOrCode(ethnicityRequestDTO.getName()
                , ethnicityRequestDTO.getCode()))
                .willReturn(EthnicityTestUtils.getEthnicityObjectForCode());

        ethnicityService.createEthnicity(ethnicityRequestDTO);
    }

    @Test
    public void createEnthicity_ShouldCreateEthnicity() {

        EthnicityRequestDTO ethnicityRequestDTO = getEthnicityRequestDTO();
        Ethnicity ethnicity = convertToEthnicityInfo(ethnicityRequestDTO);

        given(ethnicityRepository.fetchEthnicityByNameOrCode(ethnicityRequestDTO.getName()
                , ethnicityRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        ethnicityService.createEthnicity(ethnicityRequestDTO);

        assertNotNull(ethnicity);

        verify(ethnicityRepository).save(any(Ethnicity.class));
    }

    @Test
    public void deleteEthnicity_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(ethnicityRepository.fetchEthnicityById(1L)).willReturn(Optional.empty());

        ethnicityService.deleteEthnicity(getDeleteReuqestDTO());
    }

    @Test
    public void deleteEthnicity_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteReuqestDTO();

        Ethnicity ethnicity = updateEthnicity
                .apply(getEthnicityInfo(), deleteRequestDTO);

        given(ethnicityRepository.fetchEthnicityById(1L))
                .willReturn(Optional.of(getEthnicityInfo()));

        ethnicityService.deleteEthnicity(deleteRequestDTO);

        assertTrue(ethnicity.getStatus() == deleteRequestDTO.getStatus()
                && ethnicity.getRemarks() == deleteRequestDTO.getRemarks());

        verify(ethnicityRepository).save(any(Ethnicity.class));
    }

    @Test
    public void updateEthnicity_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(ethnicityRepository.fetchEthnicityById(1L)).willReturn(Optional.empty());

        ethnicityService.updateEthnicity(EthnicityTestUtils.getUpdateEthnicityRequestDTO());
    }

    @Test
    public void updateEthnicity_ShouldReturnNameAlreadyExists() {
        Ethnicity ethnicity = getEthnicityInfo();
        EthnicityUpdateRequestDTO ethnicityUpdateRequestDTO = EthnicityTestUtils
                .getUpdateEthnicityRequestDTO();
        thrown.expect(DataDuplicationException.class);

        given(ethnicityRepository.fetchEthnicityById(ethnicityUpdateRequestDTO.getId()))
                .willReturn(Optional.of(ethnicity));
        given(ethnicityRepository.checkEthnicityNameAndCodeIfExist(
                ethnicityUpdateRequestDTO.getId(),
                ethnicityUpdateRequestDTO.getName(),
                ethnicityUpdateRequestDTO.getCode()))
                .willReturn(getEthnicityObjectForNameAndCode());

        ethnicityService.updateEthnicity(ethnicityUpdateRequestDTO);
    }

    @Test
    public void updateEthnicity_ShouldUpdate() {
        Ethnicity ethnicity = getEthnicityInfo();
        EthnicityUpdateRequestDTO ethnicityUpdateRequestDTO = EthnicityTestUtils
                .getUpdateEthnicityRequestDTO();
        Ethnicity ethnicityUpdated = updateEthnicity.apply(getEthnicityInfo(),
                ConvertToDeleteRequestDTO.apply(ethnicityUpdateRequestDTO.getRemarks(),
                        ethnicityUpdateRequestDTO.getStatus()));

        given(ethnicityRepository.fetchEthnicityById(ethnicityUpdateRequestDTO.getId()))
                .willReturn(Optional.of(ethnicity));
        given(ethnicityRepository.checkEthnicityNameAndCodeIfExist(
                ethnicityUpdateRequestDTO.getId(),
                ethnicityUpdateRequestDTO.getName(),
                ethnicityUpdateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());


        ethnicityService.updateEthnicity(ethnicityUpdateRequestDTO);

        assertTrue(ethnicityUpdated.getStatus() == ethnicityUpdateRequestDTO.getStatus()
                && ethnicityUpdated.getRemarks() == ethnicityUpdateRequestDTO.getRemarks());

        verify(ethnicityRepository).save(any(Ethnicity.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchEthnicity_ShouldReturnNoContentFound() {

        EthnicitySearchRequestDTO ethnicitySearchRequestDTO = getEthnicitySeacrhRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(ethnicityRepository.searchEthnicity(ethnicitySearchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Ethnicity.class));

        ethnicityService.searchEthnicity(ethnicitySearchRequestDTO, pageable);
    }

    @Test
    public void searchEthnicity_ShouldEthnicity() {
        EthnicitySearchRequestDTO ethnicitySearchRequestDTO = getEthnicitySeacrhRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(ethnicityRepository.searchEthnicity(ethnicitySearchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        ethnicityService.searchEthnicity(ethnicitySearchRequestDTO, pageable);

        verify(ethnicityRepository).searchEthnicity(ethnicitySearchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchEthnicityDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(ethnicityRepository.fetchEthnicityDetails(id))
                .willThrow(new NoContentFoundException(Ethnicity.class, "id", id.toString()));

        ethnicityService.fetchEthnicityDetails(id);

    }

    @Test
    public void fetchEthnicityDetails_ShouldReturnData() {
        EthnicityResponseDTO responseDTOS = EthnicityTestUtils.
                getEthnicityResponseDTO();


        given(ethnicityRepository.fetchEthnicityDetails(1L))
                .willReturn((responseDTOS));

        ethnicityService.fetchEthnicityDetails(1L);

        verify(ethnicityRepository).fetchEthnicityDetails(1L);

    }


    @Test
    public void fetchEthnicity_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(ethnicityRepository.fetchEthnicityById(1L))
                .willReturn(Optional.empty());

        ethnicityService.fetchEthnicity(1L);

    }

    @Test
    public void fetchEthnicity_ShouldReturnData() {

        given(ethnicityRepository.fetchEthnicityById(1L))
                .willReturn(Optional.of((getEthnicityInfo())));

        ethnicityService.fetchEthnicity(1L);

        verify(ethnicityRepository).fetchEthnicityById(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(ethnicityRepository.dropDownList())
                .willReturn(Optional.empty());

        ethnicityService.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<EthnicityDropDownResponseDTO> ethnicityDropDownResponseDTO = getDropDownInfo();

        given(ethnicityRepository.dropDownList())
                .willReturn(Optional.of(ethnicityDropDownResponseDTO));

        ethnicityService.fetchDropDownList();

        verify(ethnicityRepository).dropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(ethnicityRepository.activeDropDownList())
                .willReturn(Optional.empty());

        ethnicityService.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<EthnicityDropDownResponseDTO> ethnicityDropDownResponseDTO = getDropDownInfo();

        given(ethnicityRepository.activeDropDownList())
                .willReturn(Optional.of(ethnicityDropDownResponseDTO));

        ethnicityService.fetchActiveDropDownList();

        verify(ethnicityRepository).activeDropDownList();
    }


}
