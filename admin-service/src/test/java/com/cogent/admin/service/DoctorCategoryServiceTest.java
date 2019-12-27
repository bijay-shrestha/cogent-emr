package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DoctorCategoryRepository;
import com.cogent.admin.service.impl.DoctorCategoryServiceImpl;
import com.cogent.persistence.model.DoctorCategory;
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

import static com.cogent.admin.dto.doctorcategory.DoctorCategoryTestUtils.*;
import static com.cogent.admin.utils.DoctorCategoryUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DoctorCategoryServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    DoctorCategoryRepository repository;

    @InjectMocks
    DoctorCategoryServiceImpl service;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createDoctorCategory_ShouldReturnNameAndCodeExists();
        createDoctorCategory_ShouldReturnNameExists();
        createDoctorCategory_ShouldReturnCodeExists();
        createDoctorCategory_ShouldCreateDoctorCategory();
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
        fetchDoctorCategoryDetails_ShouldReturnNoContentFound();
        fetchDoctorCategoryDetails_ShouldReturnData();
    }

    @Test
    public void delete() {
        deleteDoctorCategory_ShouldReturnNoContentFound();
        deleteDoctorCategory_ShouldDelete();
    }

    @Test
    public void update() {
        updateDoctorCategory_ShouldReturnNoContentFound();
        updateDoctorCategory_ShouldReturnNameAlreadyExists();
        updateDoctorCategory_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchDoctorCategory_ShouldReturnNoContentFound();
        searchDoctorCategory_ShouldReturnDoctorCategory();
    }

    @Test
    public void createDoctorCategory_ShouldReturnNameAndCodeExists() {

        thrown.expect(DataDuplicationException.class);

        DoctorCategoryRequestDTO requestDTO = getDoctorCategoryRequestDTO();

        given(repository.fetchDoctorCategoryByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getDoctorCategoryObjectForNameAndCode());

        service.createDoctorCategory(requestDTO);

        verify(repository)
                .fetchDoctorCategoryByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createDoctorCategory_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        DoctorCategoryRequestDTO requestDTO = getDoctorCategoryRequestDTO();

        given(repository.fetchDoctorCategoryByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getDoctorCategoryObjectForName());

        service.createDoctorCategory(requestDTO);

        verify(repository)
                .fetchDoctorCategoryByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createDoctorCategory_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        DoctorCategoryRequestDTO requestDTO = getDoctorCategoryRequestDTO();

        given(repository.fetchDoctorCategoryByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getDoctorCategoryObjectForCode());

        service.createDoctorCategory(requestDTO);

        verify(repository)
                .fetchDoctorCategoryByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createDoctorCategory_ShouldCreateDoctorCategory() {

        DoctorCategoryRequestDTO requestDTO = getDoctorCategoryRequestDTO();

        DoctorCategory expected = convertToDoctorCategory(requestDTO);

        given(repository.fetchDoctorCategoryByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        service.createDoctorCategory(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getDoctorCategoryInfo()));

        verify(repository)
                .save(any(DoctorCategory.class));
    }

    @Test
    public void deleteDoctorCategory_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDoctorCategoryById(1L))
                .willReturn(Optional.empty());

        service.deleteDoctorCategory(getDeleteRequestDTO());

        verify(repository)
                .fetchDoctorCategoryById(1L);

    }

    @Test
    public void deleteDoctorCategory_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        DoctorCategory expected = deleteDoctorCategory
                .apply(getDoctorCategoryInfo(), deleteRequestDTO);

        given(repository.fetchDoctorCategoryById(1L))
                .willReturn(Optional.of(getDoctorCategoryInfo()));

        service.deleteDoctorCategory(deleteRequestDTO);

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDoctorCategoryInfoToDelete()));

        verify(repository)
                .fetchDoctorCategoryById(1L);

    }


    @Test
    public void updateDoctorCategory_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDoctorCategoryById(1L))
                .willReturn(Optional.empty());

        service.updateDoctorCategory(getDoctorCategoryUpdateRequestDTO());

        verify(repository)
                .fetchDoctorCategoryById(1L);
    }

    @Test
    public void updateDoctorCategory_ShouldReturnNameAlreadyExists() {

        DoctorCategoryUpdateRequestDTO requestDTO = getDoctorCategoryUpdateRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(repository.fetchDoctorCategoryById(requestDTO.getId()))
                .willReturn(Optional.of(getDoctorCategoryInfo()));

        given(repository.checkIfDoctorCategoryNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getDoctorCategoryObjectForNameAndCode());

        service.updateDoctorCategory(requestDTO);

        verify(repository)
                .fetchDoctorCategoryById(requestDTO.getId());

        verify(repository)
                .checkIfDoctorCategoryNameAndCodeExists(requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void updateDoctorCategory_ShouldUpdate() {

        DoctorCategoryUpdateRequestDTO requestDTO = getDoctorCategoryUpdateRequestDTO();

        DoctorCategory expected = updateDoctorCategory.apply(getDoctorCategoryInfo(), requestDTO);

        given(repository.fetchDoctorCategoryById(requestDTO.getId()))
                .willReturn(Optional.of(getDoctorCategoryInfo()));

        given(repository.checkIfDoctorCategoryNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        service.updateDoctorCategory(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDoctorCategoryInfoToUpdate()));

        verify(repository)
                .fetchDoctorCategoryById(requestDTO.getId());

        verify(repository)
                .checkIfDoctorCategoryNameAndCodeExists(requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());

    }


    @Test(expected = NoContentFoundException.class)
    public void searchDoctorCategory_ShouldReturnNoContentFound() {

        DoctorCategorySearchRequestDTO requestDTO = getDoctorCategorySearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchDoctorCategory(requestDTO, pageable))
                .willThrow(new NoContentFoundException(DoctorCategory.class));

        service.searchDoctorCategory(requestDTO, pageable);

        verify(repository)
                .searchDoctorCategory(requestDTO, pageable);
    }

    @Test
    public void searchDoctorCategory_ShouldReturnDoctorCategory() {

        DoctorCategorySearchRequestDTO requestDTO = getDoctorCategorySearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchDoctorCategory(requestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        service.searchDoctorCategory(requestDTO, pageable);

        verify(repository)
                .searchDoctorCategory(requestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDoctorCategoryDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(repository.fetchDoctorCategoryDetails(id))
                .willThrow(new NoContentFoundException(DoctorCategory.class, "id", id.toString()));

        service.fetchDoctorCategoryDetails(1L);

        verify(repository)
                .fetchDoctorCategoryDetails(id);

    }

    @Test
    public void fetchDoctorCategoryDetails_ShouldReturnData() {

        given(repository.fetchDoctorCategoryDetails(1L))
                .willReturn((getDoctorCategoryResponseDTO()));

        service.fetchDoctorCategoryDetails(1L);

        verify(repository)
                .fetchDoctorCategoryDetails(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDropDownList())
                .willReturn(Optional.empty());

        service.doctorCategoryDropdown();

        verify(repository)
                .fetchDropDownList();

    }

    @Test
    public void dropDownList_ShouldData() {
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(repository.fetchDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        service.doctorCategoryDropdown();

        verify(repository)
                .fetchDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        service.activeDoctorCategoryDropdown();

        verify(repository)
                .fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldData() {
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(repository.fetchActiveDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        service.activeDoctorCategoryDropdown();

        verify(repository)
                .fetchActiveDropDownList();
    }







}
