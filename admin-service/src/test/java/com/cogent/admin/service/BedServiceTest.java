package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedRequestDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BedRepository;
import com.cogent.admin.service.impl.BedServiceImpl;
import com.cogent.persistence.model.Bed;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.bed.BedTestUtils.*;
import static com.cogent.admin.utils.BedUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 11/1/19
 */
public class BedServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    BedRepository bedRepository;

    @InjectMocks
    BedServiceImpl bedService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void create() {
        createBed_ShouldReturnNameAndCodeExists();
        createBed_ShouldReturnNameExists();
        createBed_ShouldReturnCodeExists();
        createBed_ShouldCreateBed();
    }

    @Test
    public void delete() {
        deleteBed_ShouldReturnNoContentFound();
        deleteBed_ShouldDelete();
    }

    @Test
    public void update() {
        updateBed_ShouldReturnNoContentFound();
        updateBed_ShouldReturnNameAlreadyExists();
        updateBed_ShouldUpdate();
    }

    @Test
    public void search() {
        searchBed_ShouldReturnNoContentFound();
        searchBed_ShouldReturnBed();
    }

    @Test
    public void fetchBedDetails() {
        fetchBedDetails_ShouldReturnNoContentFound();
        fetchBedDetails_ShouldReturnData();
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
    public void createBed_ShouldReturnNameAndCodeExists() {
        thrown.expect(DataDuplicationException.class);

        BedRequestDTO requestDTO = getBedRequestDTO();

        given(bedRepository.findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getBedObjectForNameAndCode());

        bedService.createBed(requestDTO);

        verify(bedRepository).findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode());
    }

    @Test
    public void createBed_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        BedRequestDTO requestDTO = getBedRequestDTO();

        given(bedRepository.findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getBedObjectForName());

        bedService.createBed(requestDTO);

        verify(bedRepository).findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode());
    }

    @Test
    public void createBed_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        BedRequestDTO requestDTO = getBedRequestDTO();

        given(bedRepository.findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getBedObjectForCode());

        bedService.createBed(requestDTO);

        verify(bedRepository).findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode());
    }


    @Test
    public void createBed_ShouldCreateBed() {
        BedRequestDTO requestDTO = getBedRequestDTO();

        Bed expected = parseToBed.apply(requestDTO);

        given(bedRepository.findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        bedService.createBed(requestDTO);

        assertNotNull(expected);

        verify(bedRepository).findBedByNameOrCode(requestDTO.getName()
                , requestDTO.getCode());

        verify(bedRepository).save(any(Bed.class));

    }

    @Test
    public void deleteBed_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(bedRepository.fetchBedById(1L))
                .willReturn(Optional.empty());

        bedService.deleteBed(getDeleteRequestDTO());

        verify(bedRepository).fetchBedById(1L);
    }

    @Test
    public void deleteBed_ShouldDelete() {

        Bed expected = deleteBed.apply(getBedInfo(), getDeleteRequestDTO());

        given(bedRepository.fetchBedById(1L))
                .willReturn(Optional.ofNullable(getBedInfo()));


        bedService.deleteBed(getDeleteRequestDTO());

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDeletedBedInfo()));

        verify(bedRepository).fetchBedById(1L);
    }

    @Test
    public void updateBed_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(bedRepository.fetchBedById(1L))
                .willReturn(Optional.empty());

        bedService.updateBed(getUpdateBedRequestDTO());

        verify(bedRepository).fetchBedById(1L);
    }

    @Test
    public void updateBed_ShouldReturnNameAlreadyExists() {

        BedUpdateRequestDTO updateRequestDTO = getUpdateBedRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(bedRepository.fetchBedById(1L))
                .willReturn(Optional.of(getBedInfo()));

        given(bedRepository.checkBedNameAndCodeIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName(),updateRequestDTO.getCode()))
                .willReturn(getBedObjectForNameAndCode());

        bedService.updateBed(updateRequestDTO);

        verify(bedRepository).fetchBedById(1L);

        verify(bedRepository)
                .checkBedNameAndCodeIfExist(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());
    }

    @Test
    public void updateBed_ShouldUpdate() {

        BedUpdateRequestDTO updateRequestDTO = getUpdateBedRequestDTO();

        Bed expected = updateBed.apply(getBedInfo(), updateRequestDTO);

        given(bedRepository.fetchBedById(1L))
                .willReturn(Optional.of(getBedInfo()));
        given(bedRepository.checkBedNameAndCodeIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName(),updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        bedService.updateBed(updateRequestDTO);

        Assert.assertTrue(new ReflectionEquals(expected).matches(getUpdatedBedInfo()));

        verify(bedRepository).fetchBedById(1L);

        verify(bedRepository)
                .checkBedNameAndCodeIfExist(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());

    }

    @Test(expected = NoContentFoundException.class)
    public void searchBed_ShouldReturnNoContentFound() {

        BedSearchRequestDTO searchRequestDTO = getSearchBedRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(bedRepository.searchBed(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Bed.class));

        bedService.searchBed(searchRequestDTO, pageable);

        verify(bedRepository).searchBed(searchRequestDTO, pageable);
    }

    @Test
    public void searchBed_ShouldReturnBed() {
        BedSearchRequestDTO searchRequestDTO = getSearchBedRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(bedRepository.searchBed(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        bedService.searchBed(searchRequestDTO, pageable);

        verify(bedRepository).searchBed(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchBedDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(bedRepository.fetchBedDetails(id))
                .willThrow(new NoContentFoundException(Bed.class, "id", id.toString()));

        bedService.fetchBedDetails(id);

        verify(bedRepository).fetchBedDetails(id);

    }

    @Test
    public void fetchBedDetails_ShouldReturnData() {

        given(bedRepository.fetchBedDetails(1L))
                .willReturn(getBedResponseDTO());

        bedService.fetchBedDetails(1L);

        verify(bedRepository).fetchBedDetails(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(bedRepository.dropDownList())
                .willReturn(Optional.empty());

        bedService.fetchDropDownList();

        verify(bedRepository).dropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(bedRepository.dropDownList())
                .willReturn(Optional.of(dropDownInfo));

        bedService.fetchDropDownList();

        verify(bedRepository).dropDownList();

    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(bedRepository.activeDropDownList())
                .willReturn(Optional.empty());

        bedService.fetchActiveDropDownList();

        verify(bedRepository).activeDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(bedRepository.activeDropDownList())
                .willReturn(Optional.of(dropDownInfo));

        bedService.fetchActiveDropDownList();

        verify(bedRepository).activeDropDownList();
    }


    @Test
    public void test(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(formatter.format(new Date()));

        System.out.println(formatter);

    }

}
