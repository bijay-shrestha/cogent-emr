package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitRequestDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.UnitRepository;
import com.cogent.admin.repository.WardRepository;
import com.cogent.admin.service.impl.UnitServiceImpl;
import com.cogent.persistence.model.Unit;
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
import static com.cogent.admin.dto.unit.UnitTestUtils.*;
import static com.cogent.admin.utils.UnitUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi  on 10/2/19
 */

public class UnitServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    UnitRepository unitRepository;

    @Mock
    WardRepository wardRepository;

    @InjectMocks
    UnitServiceImpl unitService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createUnit_ShouldReturnNameAndCodeExists();
        createUnit_ShouldReturnNameExists();
        createUnit_ShouldReturnCodeExists();
        createUnit_ShouldCreateUnit();
    }

    @Test
    public void delete() {
        deleteUnit_ShouldReturnNoContentFound();
        deleteUnit_ShouldDelete();
    }

    @Test
    public void update() {
        updateUnit_ShouldReturnNoContentFound();
        updateUnit_ShouldReturnNameAlreadyExists();
        updateUnit_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchUnit_ShouldReturnNoContentFound();
        searchUnit_ShouldUnit();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetails() {
        fetchUnitDetails_ShouldReturnNoContentFound();
        fetchUnitDetails_ShouldReturnData();
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
    public void createUnit_ShouldReturnNameAndCodeExists() {
        thrown.expect(DataDuplicationException.class);

        UnitRequestDTO requestDTO = getUnitRequestDTO();

        given(unitRepository.fetchUnitByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getUnitObjectForNameAndCode());

        unitService.createUnit(requestDTO);
    }

    @Test
    public void createUnit_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        UnitRequestDTO requestDTO = getUnitRequestDTO();

        given(unitRepository.fetchUnitByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getUnitObjectForName());

        unitService.createUnit(requestDTO);
    }

    @Test
    public void createUnit_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        UnitRequestDTO requestDTO = getUnitRequestDTO();

        given(unitRepository.fetchUnitByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getUnitObjectForCode());

        unitService.createUnit(requestDTO);
    }

    @Test
    public void createUnit_ShouldCreateUnit() {

        UnitRequestDTO requestDTO = getUnitRequestDTO();
        Unit Unit = parseToUnit.apply(requestDTO);

        given(unitRepository.fetchUnitByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        unitService.createUnit(requestDTO);

        assertNotNull(Unit);

        verify(unitRepository).save(any(Unit.class));
    }

    @Test
    public void deleteUnit_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(unitRepository.fetchUnitById(1L))
                .willReturn(Optional.empty());

        unitService.deleteUnit(getDeleteRequestDTO());
    }

    @Test
    public void deleteUnit_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Unit Unit = deleteUnit
                .apply(getUnitInfo(), getDeleteRequestDTO());

        given(unitRepository.fetchUnitById(1L))
                .willReturn(Optional.of(getUnitInfo()));

        unitService.deleteUnit(getDeleteRequestDTO());

        assertTrue(Unit.getStatus() == deleteRequestDTO.getStatus()
                && Unit.getRemarks() == deleteRequestDTO.getRemarks());

        verify(unitRepository).save(any(Unit.class));
    }

    @Test
    public void updateUnit_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(unitRepository.fetchUnitById(1L)).willReturn(Optional.empty());

        unitService.updateUnit(getUpdateUnitRequestDTO());
    }

    @Test
    public void updateUnit_ShouldReturnNameAlreadyExists() {

        UnitUpdateRequestDTO updateRequestDTO = getUpdateUnitRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(unitRepository.fetchUnitById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getUnitInfo()));
        given(unitRepository.checkUnitNameAndCodeIfExist(
                updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(getUnitObjectForNameAndCode());

        unitService.updateUnit(getUpdateUnitRequestDTO());
    }

    @Test
    public void updateUnit_ShouldUpdate() {

        UnitUpdateRequestDTO updateRequestDTO = getUpdateUnitRequestDTO();

        Unit UnitUpdated = updateToUnit(getUnitInfo(), updateRequestDTO);

        given(unitRepository.fetchUnitById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getUnitInfo()));
        given(unitRepository.checkUnitNameAndCodeIfExist(
                updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        unitService.updateUnit(getUpdateUnitRequestDTO());

        assertTrue(UnitUpdated.getStatus() == updateRequestDTO.getStatus()
                && UnitUpdated.getRemarks() == updateRequestDTO.getRemarks());

        verify(unitRepository).save(any(Unit.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchUnit_ShouldReturnNoContentFound() {

        UnitSearchRequestDTO searchRequestDTO = getSearchUnitRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(unitRepository.searchUnit(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Unit.class));

        unitService.searchUnit(searchRequestDTO, pageable);
    }

    @Test
    public void searchUnit_ShouldUnit() {
        UnitSearchRequestDTO searchRequestDTO = getSearchUnitRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(unitRepository.searchUnit(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        unitService.searchUnit(searchRequestDTO, pageable);

        verify(unitRepository).searchUnit(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchUnitDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(unitRepository.fetchUnitDetails(id))
                .willThrow(new NoContentFoundException(Unit.class, "id", id.toString()));

        unitService.fetchUnitDetails(id);

    }

    @Test
    public void fetchUnitDetails_ShouldReturnData() {

        given(unitRepository.fetchUnitDetails(1L))
                .willReturn((getUnitResponseDTO()));

        unitService.fetchUnitDetails(1L);

        verify(unitRepository).fetchUnitDetails(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(unitRepository.dropDownList())
                .willReturn(Optional.empty());

        unitService.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(unitRepository.dropDownList())
                .willReturn(Optional.of(dropDownInfo));

        unitService.fetchDropDownList();

        verify(unitRepository).dropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(unitRepository.activeDropDownList())
                .willReturn(Optional.empty());

        unitService.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(unitRepository.activeDropDownList())
                .willReturn(Optional.of(dropDownInfo));

        unitService.fetchActiveDropDownList();

        verify(unitRepository).activeDropDownList();
    }

}
