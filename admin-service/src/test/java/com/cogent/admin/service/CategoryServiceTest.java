package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.category.CategoryTestUtils;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.CategoryRepository;
import com.cogent.admin.service.impl.CategoryServiceImpl;
import com.cogent.persistence.model.Category;
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

import static com.cogent.admin.dto.category.CategoryTestUtils.*;
import static com.cogent.admin.utils.CategoryUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 10/24/19
 */
public class CategoryServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createCategory_ShouldReturnNameAndCodeExists();
        createCategory_ShouldReturnNameExists();
        createCategory_ShouldReturnCodeExists();
        createCategory_ShouldCreateCategory();
    }

    @Test
    public void delete() {
        deleteCategory_ShouldReturnNoContentFound();
        deleteCategory_ShouldDelete();
    }

    @Test
    public void update_Category() {
        updateCategory_ShouldReturnNoContentFound();
        updateCategory_ShouldReturnNameAlreadyExists();
        updateCategory_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchCategory_ShouldReturnNoContentFound();
        searchCategory_ShouldCategory();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchCategoryDetails_ShouldReturnNoContentFound();
        fetchCategoryDetails_ShouldReturnData();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchCategoryById() {
        fetchCategory_ShouldReturnNoContentFound();
        fetchCategory_ShouldReturnData();
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
    public void createCategory_ShouldReturnNameAndCodeExists() {
        thrown.expect(DataDuplicationException.class);

        CategoryRequestDTO categoryRequestDTO = getCategoryRequestDTO();

        given(categoryRepository.fetchCategoryByNameOrCode(categoryRequestDTO.getName()
                , categoryRequestDTO.getCode()))
                .willReturn(getCategoryObjectForNameAndCode());

        categoryService.createCategory(categoryRequestDTO);
    }

    @Test
    public void createCategory_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        CategoryRequestDTO categoryRequestDTO = getCategoryRequestDTO();

        given(categoryRepository.fetchCategoryByNameOrCode(categoryRequestDTO.getName()
                , categoryRequestDTO.getCode()))
                .willReturn(getCategoryObjectForName());

        categoryService.createCategory(categoryRequestDTO);
    }

    @Test
    public void createCategory_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        CategoryRequestDTO categoryRequestDTO = getCategoryRequestDTO();

        given(categoryRepository.fetchCategoryByNameOrCode(categoryRequestDTO.getName()
                , categoryRequestDTO.getCode()))
                .willReturn(getCategoryObjectForCode());

        categoryService.createCategory(categoryRequestDTO);
    }

    @Test
    public void createCategory_ShouldCreateCategory() {

        CategoryRequestDTO categoryRequestDTO = getCategoryRequestDTO();
        Category category = convertToCategoryInfo(categoryRequestDTO);

        given(categoryRepository.fetchCategoryByNameOrCode(categoryRequestDTO.getName()
                , categoryRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        categoryService.createCategory(categoryRequestDTO);

        assertNotNull(category);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    public void deleteCategory_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(categoryRepository.fetchCategoryById(1L)).willReturn(Optional.empty());

        categoryService.deleteCategory(getDeleteReuqestDTO());
    }

    @Test
    public void deleteCategory_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteReuqestDTO();

        Category category = delete.apply(getCategoryInfo(), deleteRequestDTO);

        given(categoryRepository.fetchCategoryById(1L))
                .willReturn(Optional.of(getCategoryInfo()));

        categoryService.deleteCategory(deleteRequestDTO);

        assertTrue(category.getStatus() == deleteRequestDTO.getStatus()
                && category.getRemarks() == deleteRequestDTO.getRemarks());

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    public void updateCategory_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(categoryRepository.fetchCategoryById(1L)).willReturn(Optional.empty());

        categoryService.updateCategory(getCategoryUpdateRequestDTO());
    }

    @Test
    public void updateCategory_ShouldReturnNameAlreadyExists() {
        Category category = getCategoryInfo();
        CategoryUpdateRequestDTO categoryUpdateRequestDTO = getCategoryUpdateRequestDTO();
        thrown.expect(DataDuplicationException.class);

        given(categoryRepository.fetchCategoryById(categoryUpdateRequestDTO.getId()))
                .willReturn(Optional.of(category));

        given(categoryRepository.checkCategoryNameAndCodeIfExist(
                categoryUpdateRequestDTO.getId(),
                categoryUpdateRequestDTO.getName(),
                categoryUpdateRequestDTO.getCode()))
                .willReturn(getCategoryObjectForNameAndCode());

        categoryService.updateCategory(categoryUpdateRequestDTO);

    }

    @Test
    public void updateCategory_ShouldUpdate() {
        Category categoryInfo = getCategoryInfo();
        CategoryUpdateRequestDTO categoryUpdateRequestDTO = getCategoryUpdateRequestDTO();
        Category categoryUpdated = update(categoryInfo,categoryUpdateRequestDTO );

        given(categoryRepository.fetchCategoryById(categoryUpdateRequestDTO.getId()))
                .willReturn(Optional.of(categoryInfo));

        given(categoryRepository.checkCategoryNameAndCodeIfExist(
                categoryUpdateRequestDTO.getId(),
                categoryUpdateRequestDTO.getName(),
                categoryUpdateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        categoryService.updateCategory(categoryUpdateRequestDTO);

        assertTrue(categoryUpdated.getStatus() == categoryUpdateRequestDTO.getStatus()
                && categoryUpdated.getRemarks() == categoryUpdateRequestDTO.getRemarks());

        verify(categoryRepository).save(any(Category.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchCategory_ShouldReturnNoContentFound() {

        CategorySearchRequestDTO categorySearchRequestDTO = getCategorySearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(categoryRepository.searchCategory(categorySearchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Category.class));

        categoryService.searchCategory(categorySearchRequestDTO, pageable);
    }

    @Test
    public void searchCategory_ShouldCategory() {
        CategorySearchRequestDTO categorySearchRequestDTO = getCategorySearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(categoryRepository.searchCategory(categorySearchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        categoryService.searchCategory(categorySearchRequestDTO, pageable);

        verify(categoryRepository).searchCategory(categorySearchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchCategoryDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(categoryRepository.fetchCategoryDetails(id))
                .willThrow(new NoContentFoundException(Category.class, "id", id.toString()));

        categoryService.fetchCategoryDetails(id);

    }

    @Test
    public void fetchCategoryDetails_ShouldReturnData() {
        CategoryResponseDTO responseDTOS = CategoryTestUtils.
                getCategoryResponseDTO();


        given(categoryRepository.fetchCategoryDetails(1L))
                .willReturn((responseDTOS));

        categoryService.fetchCategoryDetails(1L);

        verify(categoryRepository).fetchCategoryDetails(1L);

    }


    @Test(expected =NoContentFoundException.class )
    public void fetchCategory_ShouldReturnNoContentFound() {
        given(categoryRepository.fetchActiveCategoryEntityById(1L))
                .willThrow(new NoContentFoundException(Category.class));

        categoryService.fetchActiveCategoryEntity(1L);

    }

    @Test
    public void fetchCategory_ShouldReturnData() {

        given(categoryRepository.fetchActiveCategoryEntityById(1L))
                .willReturn((getCategoryInfo()));

        categoryService.fetchActiveCategoryEntity(1L);

        verify(categoryRepository).fetchActiveCategoryEntityById(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(categoryRepository.dropDownList())
                .willReturn(Optional.empty());

        categoryService.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {

        given(categoryRepository.dropDownList())
                .willReturn(Optional.of(getDropDownInfo()));

        categoryService.fetchDropDownList();

        verify(categoryRepository).dropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(categoryRepository.activeDropDownList())
                .willReturn(Optional.empty());

        categoryService.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> categoryDropDownResponseDTO = getDropDownInfo();

        given(categoryRepository.activeDropDownList())
                .willReturn(Optional.of(categoryDropDownResponseDTO));

        categoryService.fetchActiveDropDownList();

        verify(categoryRepository).activeDropDownList();
    }

}
