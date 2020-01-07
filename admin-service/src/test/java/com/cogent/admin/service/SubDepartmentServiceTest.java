package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
import com.cogent.admin.repository.ProfileRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.impl.SubDepartmentServiceImpl;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.Ethnicity;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.SubDepartment;
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
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.profile.ProfileRequestUtils.getProfileInfo;
import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.*;
import static com.cogent.admin.utils.SubDepartmentUtils.parseToSubDepartment;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class SubDepartmentServiceTest {
    @InjectMocks
    private SubDepartmentServiceImpl subDepartmentService;

    @Mock
    private SubDepartmentRepository subDepartmentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createSubDepartment_ShouldReturnNameAndCodeExists();
        createSubDepartment_ShouldReturnNameExists();
        createSubDepartment_ShouldReturnCodeExists();
        createSubDepartment_ShouldCreateSubDepartment();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchSubDepartment_ShouldReturnNoContentFoundException();
        searchSubDepartment_ShouldReturnSubDepartment();
    }

    @Test
    public void delete() {
        deleteSubDepartment_ShouldReturnNoContentFoundException();
        deleteSubDepartment_ShouldDeleteSubDepartment();
    }

    @Test
    public void fetchDetails() {
        fetchSubDepartmentDetailsById_ShouldReturnNoContentFound();
        fetchSubDepartmentDetailsById_ShouldSubDepartmentResponseDTO();
    }

    @Test
    public void fetchDropDownList() {
        fetchSubDepartmentDropDownList_ShouldReturnNoContentFoundException();
        fetchSubDepartmentDropDownList_ShouldReturnSubDepartmentNamesAndId();
    }

    @Test
    public void fetchActiveDropDownList() {
        fetchSubDepartmentActiveDropDownList_ShouldReturnNoContentFoundException();
        fetchSubDepartmentActiveDropDownList_ShouldReturnSubDepartmentNamesAndId();
    }


    @Test
    public void update() {
        updateSubDepartment_ShouldReturnNoContentFoundException();
        updateSubDepartment_ShouldReturnNameAlreadyExists();
        updateSubDepartment_ShouldUpdateSubDepartment();
    }


    @Test
    public void createSubDepartment_ShouldReturnNameAndCodeExists() {
        SubDepartmentRequestDTO subDepartmentRequestDTO = getSubDepartmentRequestDTO();
        thrown.expect(DataDuplicationException.class);

        given(subDepartmentRepository.fetchSubDepartmentByNameAndCode(subDepartmentRequestDTO.getName()
                , subDepartmentRequestDTO.getCode()))
                .willReturn(getSubDepartmentObjectForNameAndCode());

        subDepartmentService.createSubDepartment(subDepartmentRequestDTO);
    }

    @Test
    public void createSubDepartment_ShouldReturnNameExists() {
        SubDepartmentRequestDTO subDepartmentRequestDTO = getSubDepartmentRequestDTO();
        thrown.expect(DataDuplicationException.class);

        given(subDepartmentRepository.fetchSubDepartmentByNameAndCode(subDepartmentRequestDTO.getName()
                , subDepartmentRequestDTO.getCode()))
                .willReturn(getSubDepartmentObjectForName());

        subDepartmentService.createSubDepartment(subDepartmentRequestDTO);
    }

    @Test
    public void createSubDepartment_ShouldReturnCodeExists() {
        SubDepartmentRequestDTO subDepartmentRequestDTO = getSubDepartmentRequestDTO();
        thrown.expect(DataDuplicationException.class);

        given(subDepartmentRepository.fetchSubDepartmentByNameAndCode(subDepartmentRequestDTO.getName()
                , subDepartmentRequestDTO.getCode()))
                .willReturn(getSubDepartmentObjectForCode());

        subDepartmentService.createSubDepartment(subDepartmentRequestDTO);
    }

    @Test
    public void createSubDepartment_ShouldReturnDepartmentNotFound() {
        SubDepartmentRequestDTO subDepartmentRequestDTO = getSubDepartmentRequestDTO();
        thrown.expect(NoContentFoundException.class);

        given(subDepartmentRepository.fetchSubDepartmentByNameAndCode(subDepartmentRequestDTO.getName()
                , subDepartmentRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(departmentRepository.findDepartmentById(subDepartmentRequestDTO.getDepartmentId()))
                .willReturn(Optional.ofNullable(null));

        subDepartmentService.createSubDepartment(subDepartmentRequestDTO);
    }

    @Test
    public void createSubDepartment_ShouldCreateSubDepartment() {
        SubDepartmentRequestDTO subDepartmentRequestDTO = getSubDepartmentRequestDTO();
        Department department = getDepartmentInfo();
        SubDepartment subDepartment = parseToSubDepartment.apply(subDepartmentRequestDTO, department);

        given(subDepartmentRepository.fetchSubDepartmentByNameAndCode(subDepartmentRequestDTO.getName()
                , subDepartmentRequestDTO.getCode()))
                .willReturn(new ArrayList<>());
        given(departmentRepository.findActiveDepartmentById(subDepartmentRequestDTO.getDepartmentId()))
                .willReturn(Optional.of(getDepartmentInfo()));

        subDepartmentService.createSubDepartment(subDepartmentRequestDTO);

        assertNotNull(subDepartment);

        verify(subDepartmentRepository).save(any(SubDepartment.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchSubDepartment_ShouldReturnNoContentFoundException() {

        SubDepartmentSearchRequestDTO subDepartmentRequestDTO = new SubDepartmentSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(subDepartmentRepository.searchSubDepartment(subDepartmentRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Ethnicity.class));

        subDepartmentService.searchSubDepartment(subDepartmentRequestDTO, pageable);
    }

    @Test
    public void searchSubDepartment_ShouldReturnSubDepartment() {
        List<SubDepartmentMinimalResponseDTO> responseDTOS = SubDepartmentMinimalResponseDTO();
        SubDepartmentSearchRequestDTO subDepartmentRequestDTO = new SubDepartmentSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(subDepartmentRepository.searchSubDepartment(subDepartmentRequestDTO, pageable))
                .willReturn(responseDTOS);

        Assert.assertThat((subDepartmentService.searchSubDepartment(subDepartmentRequestDTO, pageable)),
                hasSize(responseDTOS.size()));

        verify(subDepartmentRepository).searchSubDepartment(subDepartmentRequestDTO, pageable);
    }


    @Test
    public void deleteSubDepartment_ShouldReturnNoContentFoundException() {
        DeleteRequestDTO deleteSubDepartmentRequestDTO = getDeleteRequestDTO();

        thrown.expect(NoContentFoundException.class);

        given(subDepartmentRepository.fetchSubDepartmentById(1L))
                .willReturn(Optional.ofNullable(null));

        subDepartmentService.deleteSubDepartment(deleteSubDepartmentRequestDTO);
    }

    @Test
    public void deleteSubDepartment_ShouldDeleteSubDepartment() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Profile profile = getProfileInfo();

        given(subDepartmentRepository.fetchSubDepartmentById(1L))
                .willReturn(Optional.of(getSubDepartmentInfo()));
        given(subDepartmentRepository.save(any(SubDepartment.class)))
                .willReturn(getDeletedSubDepartmentInfo());


        given(profileRepository.findProfileBySubDepartmentId(1L)).willReturn(profile);


        subDepartmentService.deleteSubDepartment(deleteRequestDTO);

        verify(profileRepository).save(any(Profile.class));

        verify(subDepartmentRepository).save(any(SubDepartment.class));
    }

    @Test
    public void fetchSubDepartmentDropDownList_ShouldReturnNoContentFoundException() {
        thrown.expect(NoContentFoundException.class);

        given(subDepartmentRepository.fetchDropDownList()).willReturn(Optional.empty());

        subDepartmentService.dropDownList();
    }


    @Test
    public void fetchSubDepartmentDropDownList_ShouldReturnSubDepartmentNamesAndId() {
        List<SubDepartmentDropDownDTO> dropDownDTOS = SubDepartmentDropDownDTO();

        given(subDepartmentRepository.fetchDropDownList()).willReturn(Optional.of(dropDownDTOS));

        Assert.assertThat((subDepartmentService.dropDownList()), hasSize(dropDownDTOS.size()));
        verify(subDepartmentRepository).fetchDropDownList();
    }

    @Test
    public void fetchSubDepartmentActiveDropDownList_ShouldReturnNoContentFoundException() {
        thrown.expect(NoContentFoundException.class);

        given(subDepartmentRepository.fetchActiveDropDownList()).willReturn(Optional.empty());

        subDepartmentService.activeDropDownList();
    }

    @Test
    public void fetchSubDepartmentActiveDropDownList_ShouldReturnSubDepartmentNamesAndId() {
        List<SubDepartmentDropDownDTO> dropDownDTOS = SubDepartmentDropDownDTO();

        given(subDepartmentRepository.fetchActiveDropDownList()).willReturn(Optional.of(dropDownDTOS));

        Assert.assertThat((subDepartmentService.activeDropDownList()), hasSize(dropDownDTOS.size()));
        verify(subDepartmentRepository).fetchActiveDropDownList();
    }


    @Test
    public void updateSubDepartment_ShouldReturnNoContentFoundException() {
        SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO = getsubDepartmentUpdateRequestDTO();
        thrown.expect(NoContentFoundException.class);

        given(subDepartmentRepository.fetchSubDepartmentById(4L)).willReturn(Optional.ofNullable(null));

        subDepartmentService.updateSubDepartment(subDepartmentUpdateRequestDTO);
    }

    @Test
    public void updateSubDepartment_ShouldReturnNameAlreadyExists() {
        SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO = getsubDepartmentUpdateRequestDTO();
        SubDepartment subDepartment = getSubDepartmentInfo();
        thrown.expect(DataDuplicationException.class);

        given(subDepartmentRepository.fetchSubDepartmentById(4L))
                .willReturn(Optional.of(subDepartment));
        given(subDepartmentRepository.checkSubDepartmentNameAndCodeIfExist(
                subDepartmentUpdateRequestDTO.getId(),
                subDepartmentUpdateRequestDTO.getName(),
                subDepartmentUpdateRequestDTO.getCode()))
                .willReturn(getSubDepartmentObjectForNameAndCode());

        subDepartmentService.updateSubDepartment(subDepartmentUpdateRequestDTO);
    }

    @Test
    public void updateSubDepartment_ShouldReturnNoDepartmentFound() {
        thrown.expect(NoContentFoundException.class);

        SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO = getsubDepartmentUpdateRequestDTO();
        SubDepartment subDepartment = getSubDepartmentInfo();

        given(subDepartmentRepository.fetchSubDepartmentById(4L))
                .willReturn(Optional.of(subDepartment));
        given(subDepartmentRepository.checkSubDepartmentNameAndCodeIfExist(
                subDepartmentUpdateRequestDTO.getId(),
                subDepartmentUpdateRequestDTO.getName(),
                subDepartmentUpdateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(departmentRepository.findActiveDepartmentById(subDepartmentUpdateRequestDTO.getDepartmentId()))
                .willReturn(Optional.empty());


        subDepartmentService.updateSubDepartment(subDepartmentUpdateRequestDTO);
    }

    @Test
    public void updateSubDepartment_ShouldUpdateSubDepartment() {

        SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO = getsubDepartmentUpdateRequestDTO();

        SubDepartment subDepartment = getSubDepartmentInfo();

        given(subDepartmentRepository.fetchSubDepartmentById(4L))
                .willReturn(Optional.of(subDepartment));
        given(subDepartmentRepository.checkSubDepartmentNameAndCodeIfExist(
                subDepartmentUpdateRequestDTO.getId(),
                subDepartmentUpdateRequestDTO.getName(),
                subDepartmentUpdateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());
        given(departmentRepository.findActiveDepartmentById(subDepartmentUpdateRequestDTO.getDepartmentId()))
                .willReturn(Optional.of(getDepartmentInfo()));

        given(profileRepository.findProfileBySubDepartmentId(4L)).willReturn(getProfileInfo());


        subDepartmentService.updateSubDepartment(subDepartmentUpdateRequestDTO);

        verify(profileRepository).save(any(Profile.class));

        verify(subDepartmentRepository).save(any(SubDepartment.class));
    }

    @Test
    public void fetchSubDepartmentDetailsById_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(subDepartmentRepository.fetchSubDepartmentDetail(1L))
                .willReturn(Optional.ofNullable(null));

        subDepartmentService.fetchSubDepartmentDetailsById(1L);
    }


    @Test
    public void fetchSubDepartmentDetailsById_ShouldSubDepartmentResponseDTO() {
        SubDepartmentResponseDTO subDepartmentResponseDTO = getSubDepartmentResponseDTO();

        given(subDepartmentRepository.fetchSubDepartmentDetail(1L))
                .willReturn(Optional.of(subDepartmentResponseDTO));

        Assert.assertNotNull(subDepartmentService.fetchSubDepartmentDetailsById(1L));

        verify(subDepartmentRepository).fetchSubDepartmentDetail(1L);
    }


}

