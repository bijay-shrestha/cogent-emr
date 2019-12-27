package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.department.DepartmentTestUtils;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.impl.DepartmentServiceImpl;
import com.cogent.admin.service.impl.SubDepartmentServiceImpl;
import com.cogent.admin.utils.DepartmentUtils;
import com.cogent.persistence.model.Department;
import org.junit.Assert;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.department.DepartmentTestUtils.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DepartmentServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    SubDepartmentRepository subDepartmentRepository;

    @Mock
    SubDepartmentServiceImpl subDepartmentService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createDepartment_ShouldReturnNameAndCodeExists();
        createDepartment_ShouldReturnNameExists();
        createDepartment_ShouldReturnCodeExists();
        createDepartment_ShouldCreateData();
    }

    @Test
    public void fetchNamesAndId() {
        fetchDropDownList_ShouldReturnNoContentFoundException();
        fetchDropDownList_ShouldReturnDepartmentNames();
    }


    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchDepartment_ShouldReturnNoContentFoundException();
        searchDepartment_ShouldReturnDepartment();
    }

    @Test
    public void delete() {
        deleteDepartment_ShouldReturnNoContentFoundException();
        deleteDepartment_ShouldDeleteData();
    }

    @Test
    public void update() {
        updateDepartment_ShouldReturnNoContentFoundException();
        updateDepartment_ShouldReturnNameAlreadyExists();
        updateDepartment_ShouldUpdateDepartment();
    }

    @Test
    public void searchById() {
        searchDepartmentById_ShouldReturnNoContentFoundException();
        searchDepartmentById_ShouldReturnDepartment();
    }

    @Test
    public void fetchDetailsById() {
        fetchDepartmentDetailsById_ShouldReturnNoContentFound();
        fetchDepartmentDetailsById_ShouldReturnDepartment();
    }


    @Test
    public void createDepartment_ShouldReturnNameAndCodeExists() {
        DepartmentRequestDTO departmentRequestDTO = getDepartmentRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(departmentRepository.fetchDepartmentByNameOrCode(departmentRequestDTO.getName()
                , departmentRequestDTO.getCode()))
                .willReturn(DepartmentTestUtils.getDepartmentObjectForNameAndCode());

        departmentService.createDepartment(departmentRequestDTO);
    }

    @Test
    public void createDepartment_ShouldReturnNameExists() {
        DepartmentRequestDTO departmentRequestDTO = getDepartmentRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(departmentRepository.fetchDepartmentByNameOrCode(departmentRequestDTO.getName()
                , departmentRequestDTO.getCode()))
                .willReturn(DepartmentTestUtils.getDepartmentObjectForName());

        departmentService.createDepartment(departmentRequestDTO);
    }


    @Test
    public void createDepartment_ShouldReturnCodeExists() {
        DepartmentRequestDTO departmentRequestDTO = getDepartmentRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(departmentRepository.fetchDepartmentByNameOrCode(departmentRequestDTO.getName()
                , departmentRequestDTO.getCode()))
                .willReturn(DepartmentTestUtils.getDepartmentObjectForCode());

        departmentService.createDepartment(departmentRequestDTO);
    }

    @Test
    public void createDepartment_ShouldCreateData() {
        DepartmentRequestDTO departmentRequestDTO = getDepartmentRequestDTO();

        Department department = DepartmentUtils
                .parseToDepartment(departmentRequestDTO);

        given(departmentRepository.fetchDepartmentByNameOrCode(departmentRequestDTO.getName()
                , departmentRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        departmentService.createDepartment(departmentRequestDTO);

        assertNotNull(department);

        verify(departmentRepository).save(any(Department.class));
    }


    @Test
    public void fetchDropDownList_ShouldReturnNoContentFoundException() {
        thrown.expect(NoContentFoundException.class);

        given(departmentRepository.fetchDropDownList())
                .willReturn(Optional.empty());

        departmentService.dropDownList();
    }

    @Test
    public void fetchDropDownList_ShouldReturnDepartmentNames() {
        List<DepartmentDropDownDTO> departmentDropDownDTOS = departmentDropDownDTOS();

        given(departmentRepository.fetchDropDownList())
                .willReturn(Optional.of(departmentDropDownDTOS));

        Assert.assertThat((departmentService.dropDownList()),
                hasSize(departmentDropDownDTOS.size()));

        verify(departmentRepository).fetchDropDownList();
    }

    @Test
    public void fetchActiveDropDownList_ShouldReturnNoContentFoundException() {
        thrown.expect(NoContentFoundException.class);

        given(departmentRepository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        departmentService.activeDropDownList();
    }

    @Test
    public void fetchActiveDropDownList_ShouldReturnDepartmentNames() {
        List<DepartmentDropDownDTO> departmentDropDownDTOS = departmentDropDownDTOS();

        given(departmentRepository.fetchActiveDropDownList())
                .willReturn(Optional.of(departmentDropDownDTOS));

        Assert.assertThat((departmentService.activeDropDownList()),
                hasSize(departmentDropDownDTOS.size()));

        verify(departmentRepository).fetchActiveDropDownList();
    }

    @Test(expected = NoContentFoundException.class)
    public void searchDepartment_ShouldReturnNoContentFoundException() {

        DepartmentSearchRequestDTO departmentSearchRequestDTO = getSearchDepartmentRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(departmentRepository.searchDepartment(departmentSearchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Department.class));

        departmentService.searchDepartment(departmentSearchRequestDTO, pageable);
    }

    @Test
    public void searchDepartment_ShouldReturnDepartment() {
        List<DepartmentMinimalResponseDTO> departmentMinimalResponseDTOS = departmentResponseDTOS();
        DepartmentSearchRequestDTO departmentSearchRequestDTO = getSearchDepartmentRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(departmentRepository.searchDepartment(departmentSearchRequestDTO, pageable))
                .willReturn(departmentMinimalResponseDTOS);

        Assert.assertThat((departmentService.searchDepartment(departmentSearchRequestDTO, pageable)),
                hasSize(departmentMinimalResponseDTOS.size()));

        verify(departmentRepository).searchDepartment(departmentSearchRequestDTO, pageable);
    }

    @Test
    public void deleteDepartment_ShouldReturnNoContentFoundException() {
        thrown.expect(NoContentFoundException.class);

        given(departmentRepository.findDepartmentById(1L))
                .willReturn(Optional.ofNullable(null));

        departmentService.deleteDepartment(DepartmentTestUtils
                .getDeleteRequestDTO());
    }


    @Test
    public void deleteDepartment_ShouldDeleteData() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(departmentRepository.findDepartmentById(1L))
                .willReturn(Optional.of(getDepartmentInfo()));

        given(subDepartmentRepository.fetchSubDepartmentToDeleteByDepartmentId(1L))
                .willReturn(getDeleteRequestDTOS());

        departmentService.deleteDepartment(deleteRequestDTO);

        verify(subDepartmentRepository).fetchSubDepartmentToDeleteByDepartmentId(1L);

        verify(departmentRepository).save(any(Department.class));
    }


    @Test
    public void updateDepartment_ShouldReturnNoContentFoundException() {
        DepartmentUpdateRequestDTO updatedDepartmentRequestDTO = getupdatedDepartmentRequestDTO();
        thrown.expect(NoContentFoundException.class);

        given(departmentRepository.findDepartmentById(updatedDepartmentRequestDTO.getId()))
                .willReturn(Optional.ofNullable(null));

        departmentService.updateDepartment(updatedDepartmentRequestDTO);
    }

    @Test
    public void updateDepartment_ShouldReturnNameAlreadyExists() {
        DepartmentUpdateRequestDTO updatedDepartmentRequestDTO = getupdatedDepartmentRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(departmentRepository.findDepartmentById(updatedDepartmentRequestDTO.getId()))
                .willReturn(Optional.of(getDepartmentInfo()));
        given(departmentRepository.checkIfDepartmentNameAndCodeExists(updatedDepartmentRequestDTO.getId(),
                updatedDepartmentRequestDTO.getName(), updatedDepartmentRequestDTO.getCode()))
                .willReturn(getDepartmentObjectForNameAndCode());

        departmentService.updateDepartment(updatedDepartmentRequestDTO);
    }

    @Test
    public void updateDepartment_ShouldUpdateDepartment() {
        DepartmentUpdateRequestDTO departmentUpdateRequestDTO = getupdatedDepartmentRequestDTO();

        given(departmentRepository.findDepartmentById(departmentUpdateRequestDTO.getId()))
                .willReturn(Optional.of(getDepartmentInfo()));
        given(departmentRepository.checkIfDepartmentNameAndCodeExists(departmentUpdateRequestDTO.getId(),
                departmentUpdateRequestDTO.getName(), departmentUpdateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());
        given(subDepartmentRepository.fetchSubDepartmentIdByDepartmentId(1L))
                .willReturn(Arrays.asList(1L, 2L));
        given(subDepartmentRepository.saveAll(subDepartmentList()))
                .willReturn(subDepartmentList());

        departmentService.updateDepartment(departmentUpdateRequestDTO);

        verify(subDepartmentService).checkAndUpdateProfile(departmentUpdateRequestDTO.getId(),
                departmentUpdateRequestDTO.getRemarks(), departmentUpdateRequestDTO.getStatus());

        verify(subDepartmentRepository).fetchSubDepartmentByDepartmentId(1L);

        verify(subDepartmentRepository).saveAll(anyIterable());

        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    public void searchDepartmentById_ShouldReturnNoContentFoundException() {
        thrown.expect(NoContentFoundException.class);

        given(departmentRepository.findDepartmentById(1L))
                .willReturn(Optional.ofNullable(null));

        departmentService.fetchDepartmentById(1L);
    }

    @Test
    public void searchDepartmentById_ShouldReturnDepartment() {
        Department department = getDepartmentInfo();

        given(departmentRepository.findDepartmentById(1L))
                .willReturn(Optional.of(department));

        assertNotNull(departmentService.fetchDepartmentById(1L));

        verify(departmentRepository).findDepartmentById(1L);
    }

    @Test
    public void fetchDepartmentDetailsById_ShouldReturnNoContentFound() {

        Long id = 1L;

        thrown.expect(NoContentFoundException.class);

        given(departmentRepository.fetchDepartmentDetails(id))
                .willThrow(new NoContentFoundException(Department.class, "id", id.toString()));

        departmentService.fetchDepartmentDetails(id);
    }


    @Test
    public void fetchDepartmentDetailsById_ShouldReturnDepartment() {
        DepartmentResponseDTO departmentResponseDTO = getDepartmentReponseDTO();

        given(departmentRepository.fetchDepartmentDetails(1L))
                .willReturn(departmentResponseDTO);

        assertNotNull(departmentService.fetchDepartmentDetails(1L));

        verify(departmentRepository).fetchDepartmentDetails(1L);
    }
}



