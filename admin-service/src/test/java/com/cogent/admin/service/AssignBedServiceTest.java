package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AssignBedRepository;
import com.cogent.admin.repository.BedRepository;
import com.cogent.admin.repository.WardUnitRepository;
import com.cogent.admin.service.impl.AssignBedServiceImpl;
import com.cogent.persistence.model.AssignBed;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.assignbed.AssignBedTestUtils.*;
import static com.cogent.admin.dto.bed.BedTestUtils.getBedInfo;
import static com.cogent.admin.dto.ward.WardUnitTestUtils.getWardUnitInfo;
import static com.cogent.admin.utils.AssignBedUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 11/7/19
 */
public class AssignBedServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Mock
    AssignBedRepository assignBedRepository;

    @Mock
    BedRepository  bedRepository;

    @Mock
    WardUnitRepository wardUnitRepository;


    @InjectMocks
    AssignBedServiceImpl assignBedService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createAssignBed_ShouldReturnDataDuplicationException();
        createAssignBed_ShouldSave();
    }

    @Test
    public void delete() {
        deleteAssignBed_ShouldThrowsException();
        deleteAssignBed_ShouldDelete();
    }

    @Test
    public void update(){
        updateAssignBed_ShouldThrowNoContentFoundException();
        updateAssignBed_ShouldThrowDataDuplicationException();
        updateAssignBed_ShouldUpdate();
    }

    @Test
    public void search(){
       searchAssignedBed_ShouldReturnNoContentFound();
       searchAssignedBed_ShouldAssignedBed();
    }

    @Test
    public void fetchDetails(){
       fetchAssignBedDetails_ShouldReturnNoContentFound();
       fetchAssignBedDetails_ShouldReturnData();
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
    public void createAssignBed_ShouldReturnDataDuplicationException() {
        thrown.expect(DataDuplicationException.class);

        AssignBedRequestDTO requestDTO = getAssignBedRequestDTO();

        given(assignBedRepository.checkIfBedExixts(1L, 1L, 1L))
                .willReturn(1L);

        assignBedService.createAssignBed(requestDTO);

        verify(assignBedRepository)
                .checkIfBedExixts(1L, 1L, 1L);

    }

    @Test
    public void createAssignBed_ShouldSave() {

        AssignBedRequestDTO requestDTO = getAssignBedRequestDTO();

        given(assignBedRepository.checkIfBedExixts(1L, 1L, 1L))
                .willReturn(0L);
        given(bedRepository.fetchActiveBedById(1L)).willReturn(getBedInfo());

        given(wardUnitRepository.fetchWardUnitByUnitAndWardId(1L,1L))
                .willReturn(getWardUnitInfo());

        assignBedService.createAssignBed(requestDTO);

        verify(assignBedRepository)
                .checkIfBedExixts(1L, 1L, 1L);

        verify(bedRepository,times(2))
                .fetchActiveBedById(1L);

        verify(wardUnitRepository,times(2))
                .fetchWardUnitByUnitAndWardId(1L,1L);

        verify(assignBedRepository)
                .save(any(AssignBed.class));

    }

    @Test(expected = NoContentFoundException.class)
    public void deleteAssignBed_ShouldThrowsException() {

        DeleteRequestDTO requestDTO = getDeleteRequestDTO();

        given(assignBedRepository.fetchAssignBedById(1L))
                .willThrow(new NoContentFoundException(AssignBed.class));

        assignBedService.deleteAssignedBed(requestDTO);

        verify(assignBedRepository)
                .fetchAssignBedById(1L);
    }

    @Test
    public void deleteAssignBed_ShouldDelete() {

        DeleteRequestDTO requestDTO = getDeleteRequestDTO();

        given(assignBedRepository.fetchAssignBedById(1L))
                .willReturn(getAssignBedInfo());

        AssignBed expected = deleteAssignedBed.apply(assignBedRepository
                .fetchAssignBedById(1L), requestDTO);

        assignBedService.deleteAssignedBed(requestDTO);

        assertTrue(expected != null);

        verify(assignBedRepository,times(2))
                .fetchAssignBedById(1L);

        verify(assignBedRepository).save(any(AssignBed.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void updateAssignBed_ShouldThrowNoContentFoundException() {

        AssignBedUpdateRequestDTO requestDTO=getAssignBedUpdateRequestDTO();

        given(assignBedRepository.fetchAssignBedById(1L))
                .willThrow(new NoContentFoundException(AssignBed.class));

        assignBedService.updateAssignedBed(requestDTO);

        verify(assignBedRepository)
                .fetchAssignBedById(1L);
    }

    @Test
    public void updateAssignBed_ShouldThrowDataDuplicationException() {

        thrown.expect(DataDuplicationException.class);

        AssignBedUpdateRequestDTO requestDTO=getAssignBedUpdateRequestDTO();

        given(assignBedRepository.fetchAssignBedById(1L))
                .willReturn(getAssignBedInfo());

        given(assignBedRepository.checkIfBedExixts(1L, 1L, 1L))
                .willReturn(1L);

        assignBedService.updateAssignedBed(requestDTO);

        verify(assignBedRepository)
                .fetchAssignBedById(1L);

        verify(assignBedRepository)
                .checkIfBedExixts(1L, 1L, 1L);
    }

    @Test
    public void updateAssignBed_ShouldUpdate() {

        AssignBedUpdateRequestDTO requestDTO=getAssignBedUpdateRequestDTO();

        given(assignBedRepository.fetchAssignBedById(1L))
                .willReturn(getAssignBedInfo());

        given(assignBedRepository.checkIfBedExixts(1L, 1L, 1L))
                .willReturn(0L);

        given(bedRepository.fetchActiveBedById(1L)).willReturn(getBedInfo());

        given(wardUnitRepository.fetchWardUnitByUnitAndWardId(1L,1L))
                .willReturn(getWardUnitInfo());


        assignBedService.updateAssignedBed(requestDTO);

        verify(assignBedRepository)
                .fetchAssignBedById(1L);

        verify(assignBedRepository)
                .checkIfBedExixts(1L, 1L, 1L);

        verify(bedRepository,times(2))
                .fetchActiveBedById(1L);

        verify(wardUnitRepository,times(2))
                .fetchWardUnitByUnitAndWardId(1L,1L);

        verify(assignBedRepository)
                .save(any(AssignBed.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchAssignedBed_ShouldReturnNoContentFound() {

        AssignBedSearchRequestDTO searchRequestDTO =new AssignBedSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(assignBedRepository.searchAssignedBed(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(AssignBed.class));

        assignBedService.searchAssignedBed(searchRequestDTO, pageable);

        verify(assignBedRepository)
                .searchAssignedBed(searchRequestDTO,pageable);
    }

    @Test
    public void searchAssignedBed_ShouldAssignedBed() {
        AssignBedSearchRequestDTO searchRequestDTO =new AssignBedSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(assignBedRepository.searchAssignedBed(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        assignBedService.searchAssignedBed(searchRequestDTO, pageable);

        verify(assignBedRepository)
                .searchAssignedBed(searchRequestDTO,pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAssignBedDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(assignBedRepository.fetchAssignBedDetails(id))
                .willThrow(new NoContentFoundException(AssignBed.class, "id", id.toString()));

        assignBedService.fetchAssignedBedDetails(id);

    }

    @Test
    public void fetchAssignBedDetails_ShouldReturnData() {

        given(assignBedRepository.fetchAssignBedDetails(1L))
                .willReturn(getAssignBedResponseDTO());

        assignBedService.fetchAssignedBedDetails(1L);

       verify(assignBedRepository)
               .fetchAssignBedDetails(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(assignBedRepository.dropDownList(1L))
                .willReturn(Optional.empty());

        assignBedService.fetchDropDownList(1L);

        verify(assignBedRepository)
                .dropDownList(1L);
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(assignBedRepository.dropDownList(1L))
                .willReturn(Optional.of(dropDownInfo));

        assignBedService.fetchDropDownList(1L);

        verify(assignBedRepository)
                .dropDownList(1L);

    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(assignBedRepository.activeDropDownList(1L))
                .willReturn(Optional.empty());

        assignBedService.fetchActiveDropDownList(1L);

        verify(assignBedRepository)
                .activeDropDownList(1L);
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(assignBedRepository.activeDropDownList(1L))
                .willReturn(Optional.of(dropDownInfo));

        assignBedService.fetchActiveDropDownList(1L);

        verify(assignBedRepository)
                .activeDropDownList(1L);
    }


}
