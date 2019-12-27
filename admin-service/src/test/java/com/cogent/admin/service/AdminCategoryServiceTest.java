package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AdminCategoryRepository;
import com.cogent.admin.service.impl.AdminCategoryServiceImpl;
import com.cogent.admin.utils.AdminCategoryUtils;
import com.cogent.persistence.model.AdminCategory;
import org.assertj.core.api.Assertions;
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

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.adminCategory.AdminCategoryRequestUtils.*;
import static com.cogent.admin.dto.adminCategory.AdminCategoryResponseUtils.*;
import static com.cogent.admin.utils.AdminCategoryUtils.convertDTOToAdminCategory;
import static com.cogent.admin.utils.AdminCategoryUtils.convertToUpdatedAdminCategory;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-08-11
 */
public class AdminCategoryServiceTest {

    @InjectMocks
    private AdminCategoryServiceImpl adminCategoryService;

    @Mock
    private AdminCategoryRepository adminCategoryRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveAdminCategoryTest() {

        Should_Throw_Exception_When_Name_Or_Code_Duplicates();

        Should_Successfully_Save_AdminCategory();
    }

    @Test
    public void updateAdminCategoryTest() {
        Should_Throw_Exception_When_AdminCategory_NotFound();

        Should_Throw_Exception_When_Id_And_Name_Or_Code_Already_Exists();

        Should_Successfully_Update_AdminCategory();
    }

    @Test
    public void deleteAdminCategoryTest() {

        Should_ThrowException_When_AdminCategory_Is_Null();

        Should_SuccessFully_Delete_AdminCategory();
    }

    @Test
    public void searchAdminCategory() {

        Should_ThrowException_When_AdminCategoryList_IsEmpty();

        Should_Successfully_Return_AdminCategoryList();
    }

    @Test
    public void fetchAdminCategoryForDropDown() {
        Should_ThrowException_When_NoAdminCategoriesFound();

        Should_Return_AdminCategories();
    }

    @Test
    public void fetchAdminCategoryDetails() {
        Should_ThrowException_When_AdminCategory_With_Given_ID_Is_Null();

        Should_Return_AdminCategory_Details();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Or_Code_Duplicates() {
        AdminCategoryRequestDTO requestDTO = getAdminCategoryRequestDTO();

        given(adminCategoryRepository.findAdminCategoryByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getAdminCategoryObject());

        thrown.expect(DataDuplicationException.class);

        adminCategoryService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_AdminCategory() {
        AdminCategoryRequestDTO requestDTO = getAdminCategoryRequestDTO();
        AdminCategory expected = convertDTOToAdminCategory(requestDTO);

        given(adminCategoryRepository.findAdminCategoryByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        adminCategoryService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getAdminCategory()));

        verify(adminCategoryRepository, times(1)).save(any(AdminCategory.class));
    }

    @Test
    public void Should_Throw_Exception_When_AdminCategory_NotFound() {
        AdminCategoryUpdateRequestDTO requestDTO = getAdminCategoryRequestDTOForUpdate();

        given(adminCategoryRepository.findAdminCategoryById(requestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        adminCategoryService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Id_And_Name_Or_Code_Already_Exists() {
        AdminCategoryUpdateRequestDTO requestDTO = getAdminCategoryRequestDTOForUpdate();

        given(adminCategoryRepository.findAdminCategoryById(requestDTO.getId())).willReturn
                (Optional.of(getAdminCategory()));

        given(adminCategoryRepository.findAdminCategoryByIdAndNameOrCode(requestDTO.getId(), requestDTO.getName(),
                requestDTO.getCode())).willReturn(getAdminCategoryObject());

        thrown.expect(DataDuplicationException.class);

        adminCategoryService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_AdminCategory() {

        AdminCategoryUpdateRequestDTO requestDTO = getAdminCategoryRequestDTOForUpdate();
        AdminCategory expected = convertToUpdatedAdminCategory(requestDTO, getAdminCategory());

        given(adminCategoryRepository.findAdminCategoryById(requestDTO.getId())).willReturn
                (Optional.of(getAdminCategory()));

        given(adminCategoryRepository.findAdminCategoryByIdAndNameOrCode(requestDTO.getId(), requestDTO.getName(),
                requestDTO.getCode())).willReturn(new ArrayList<>());

        adminCategoryService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getUpdatedAdminCategory()));

        verify(adminCategoryRepository, times(1)).save(any(expected.getClass()));
    }

    @Test
    public void Should_ThrowException_When_AdminCategory_Is_Null() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(adminCategoryRepository.findAdminCategoryById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        adminCategoryService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_AdminCategory() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        AdminCategory adminCategory = getAdminCategory();

        given(adminCategoryRepository.findAdminCategoryById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(adminCategory));

        AdminCategory expected = AdminCategoryUtils.convertToDeletedAdminCategory(adminCategory, deleteRequestDTO);

        given(adminCategoryRepository.save(expected)).willReturn(getDeletedAdminCategoryInfo());

        adminCategoryService.delete(deleteRequestDTO);

        Assertions.assertThat(adminCategoryRepository.save(expected).getStatus()).isEqualTo('D');

        verify(adminCategoryRepository, times(2)).save(any(expected.getClass()));
    }

    @Test
    public void Should_ThrowException_When_AdminCategoryList_IsEmpty() {
        Pageable pageable = PageRequest.of(1, 10);
        AdminCategorySearchRequestDTO searchRequestDTO = getAdminCategoryRequestDTOForSearch();

        given(adminCategoryRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        adminCategoryService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_AdminCategoryList() {
        AdminCategorySearchRequestDTO searchRequestDTO = getAdminCategoryRequestDTOForSearch();
        List<AdminCategoryMinimalResponseDTO> expected = fetchMinimalAdminCategoryList();

        Pageable pageable = PageRequest.of(1, 10);

        given(adminCategoryRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(adminCategoryService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(adminCategoryService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));
    }

    @Test
    public void Should_ThrowException_When_NoAdminCategoriesFound() {

        given(adminCategoryRepository.fetchActiveAdminCategoryForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        adminCategoryService.fetchActiveAdminCategoryForDropDown();
    }

    @Test
    public void Should_Return_AdminCategories() {
        given(adminCategoryRepository.fetchActiveAdminCategoryForDropDown()).willReturn(fetchAdminCategoryListForDropDown());

        adminCategoryService.fetchActiveAdminCategoryForDropDown();

        assertThat(adminCategoryService.fetchActiveAdminCategoryForDropDown(),
                hasSize(fetchAdminCategoryListForDropDown().size()));

        assertThat(adminCategoryService.fetchActiveAdminCategoryForDropDown(),
                samePropertyValuesAs(fetchAdminCategoryListForDropDown()));
    }

    @Test
    public void Should_ThrowException_When_AdminCategory_With_Given_ID_Is_Null() {
        Long id = 1L;

        given(adminCategoryRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        adminCategoryService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_AdminCategory_Details() {
        Long id = 1L;
        AdminCategoryResponseDTO expected = fetchAdminCategoryDetailById();

        given(adminCategoryRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(adminCategoryService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }


}
