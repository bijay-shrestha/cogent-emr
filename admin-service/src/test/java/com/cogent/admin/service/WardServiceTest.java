package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardRequestDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.WardRepository;
import com.cogent.admin.service.impl.WardServiceImpl;
import com.cogent.persistence.model.Ward;
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

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.ward.WardTestUtils.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.WardUtils.deleteWard;
import static com.cogent.admin.utils.WardUtils.parseToWard;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi  on 10/2/19
 */

public class WardServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    WardRepository wardRepository;

    @InjectMocks
    WardServiceImpl wardService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void create() {
        createWard_ShouldReturnNameAndCodeExists();
        createWard_ShouldReturnNameExists();
        createWard_ShouldReturnCodeExists();
        createWard_ShouldCreateWard();
    }

    @Test
    public void delete() {
        deleteWard_ShouldReturnNoContentFound();
        deleteWard_ShouldDelete();
    }

    @Test
    public void update() {
        updateWard_ShouldReturnNoContentFound();
        updateWard_ShouldReturnNameAlreadyExists();
        updateWard_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchWard_ShouldReturnNoContentFound();
        searchWard_ShouldWard();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetails() {
        fetchWardDetails_ShouldReturnNoContentFound();
        fetchWardDetails_ShouldReturnData();
    }

    @Test
    public void activeDropDown() {
        activeDropDownList_ShouldReturnNoContentFound();
        activeDropDownList_ShouldReturnData();
    }

    @Test
    public void dropDown() {
        dropDownList_ShouldReturnNoContentFound();
        dropDownList_ShouldReturnData();
    }


    @Test
    public void createWard_ShouldReturnNameAndCodeExists() {
        thrown.expect(DataDuplicationException.class);

        WardRequestDTO requestDTO = getWardRequestDTO();

        given(wardRepository.fetchWardByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getWardObjectForNameAndCode());

        wardService.createWard(requestDTO);
    }

    @Test
    public void createWard_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        WardRequestDTO requestDTO = getWardRequestDTO();

        given(wardRepository.fetchWardByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getWardObjectForName());

        wardService.createWard(requestDTO);
    }

    @Test
    public void createWard_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        WardRequestDTO requestDTO = getWardRequestDTO();

        given(wardRepository.fetchWardByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getWardObjectForCode());

        wardService.createWard(requestDTO);
    }

    @Test
    public void createWard_ShouldCreateWard() {

        WardRequestDTO requestDTO = getWardRequestDTO();
        Ward ward = parseToWard(requestDTO);

        given(wardRepository.fetchWardByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        wardService.createWard(requestDTO);

        assertNotNull(ward);

        verify(wardRepository).save(any(Ward.class));
    }

    @Test
    public void deleteWard_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(wardRepository.fetchWardById(1L))
                .willReturn(Optional.empty());

        wardService.deleteWard(getDeleteRequestDTO());
    }

    @Test
    public void deleteWard_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Ward ward = deleteWard
                .apply(getWardInfo(), deleteRequestDTO);

        given(wardRepository.fetchWardById(1L))
                .willReturn(Optional.of(getWardInfo()));

        wardService.deleteWard(getDeleteRequestDTO());

        assertTrue(ward.getStatus() == deleteRequestDTO.getStatus()
                && ward.getRemarks() == deleteRequestDTO.getRemarks());

        verify(wardRepository).save(any(Ward.class));
    }

    @Test
    public void updateWard_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(wardRepository.fetchWardById(1L)).willReturn(Optional.empty());

        wardService.updateWard(getUpdateWardRequestDTO());
    }

    @Test
    public void updateWard_ShouldReturnNameAlreadyExists() {

        WardUpdateRequestDTO updateRequestDTO = getUpdateWardRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(wardRepository.fetchWardById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getWardInfo()));
        given(wardRepository.checkIfWardNameAndCodeExists(
                updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(getWardObjectForNameAndCode());

        wardService.updateWard(getUpdateWardRequestDTO());
    }

    @Test
    public void updateWard_ShouldUpdate() {

        WardUpdateRequestDTO updateRequestDTO = getUpdateWardRequestDTO();

        Ward wardUpdated = deleteWard.apply(getWardInfo(),
                ConvertToDeleteRequestDTO.apply(updateRequestDTO.getRemarks(),
                        updateRequestDTO.getStatus()));

        given(wardRepository.fetchWardById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getWardInfo()));
        given(wardRepository.checkIfWardNameAndCodeExists(
                updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        wardService.updateWard(getUpdateWardRequestDTO());

        assertTrue(wardUpdated.getStatus() == updateRequestDTO.getStatus()
                && wardUpdated.getRemarks() == updateRequestDTO.getRemarks());

        verify(wardRepository).save(any(Ward.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchWard_ShouldReturnNoContentFound() {

        WardSearchRequestDTO searchRequestDTO = getSearchWardRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(wardRepository.searchWard(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Ward.class));

        wardService.searchWard(searchRequestDTO, pageable);
    }

    @Test
    public void searchWard_ShouldWard() {
        WardSearchRequestDTO searchRequestDTO = getSearchWardRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(wardRepository.searchWard(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        wardService.searchWard(searchRequestDTO, pageable);

        verify(wardRepository).searchWard(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchWardDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(wardRepository.fetchWardDetails(id))
                .willThrow(new NoContentFoundException(Ward.class, "id", id.toString()));

        wardService.fetchWardDetails(id);

    }

    @Test
    public void fetchWardDetails_ShouldReturnData() {

        given(wardRepository.fetchWardDetails(1L))
                .willReturn((getWardResponseDTO()));

        wardService.fetchWardDetails(1L);

        verify(wardRepository).fetchWardDetails(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(wardRepository.dropDownList())
                .willReturn(Optional.empty());

        wardService.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(wardRepository.dropDownList())
                .willReturn(Optional.of(dropDownInfo));

        wardService.fetchDropDownList();

        verify(wardRepository).dropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(wardRepository.activeDropDownList())
                .willReturn(Optional.empty());

        wardService.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(wardRepository.activeDropDownList())
                .willReturn(Optional.of(dropDownInfo));

        wardService.fetchActiveDropDownList();

        verify(wardRepository).activeDropDownList();
    }

}
