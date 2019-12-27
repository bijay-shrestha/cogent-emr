package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.response.category.CategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.CategoryRepository;
import com.cogent.persistence.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * @author Sauravi  on 2019-08-25
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CategoryRepositoryCustomTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test(expected = NoContentFoundException.class)
    public void fetchCategoryDetail_ThrowsException() {
        categoryRepository.fetchCategoryDetails(0L);
    }

    @Test
    public void fetchCategoryDetail() {
        CategoryResponseDTO responseDTO = categoryRepository
                .fetchCategoryDetails(1L);

        assertNotNull(responseDTO);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = categoryRepository
                .activeDropDownList();

        assertTrue(!minimalResponseDTOS.equals(minimalResponseDTOS));
    }

    @Test
    public void dropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = categoryRepository
                .dropDownList();

        assertTrue(!minimalResponseDTOS.equals(minimalResponseDTOS));
    }

    @Test
    public void fetchCategoryByNameOrCode() {
        List<Object[]> objects = categoryRepository
                .fetchCategoryByNameOrCode("Category-b", "b");
        assertTrue(objects.size() == 0);
    }

    @Test
    public void checkCategoryNameAndCodeIfExist() {
        List<Object[]> objects = categoryRepository
                .checkCategoryNameAndCodeIfExist(0L, "Category", "Category");

        assertTrue(objects.size()==0);
    }

    @Test
    public void searchCategory() {
        Pageable pageable = PageRequest.of(1, 10);
        CategorySearchRequestDTO seacrhRequestDTO = new CategorySearchRequestDTO();

       List<CategoryMinimalResponseDTO> minimalResponseDTOS = categoryRepository
                .searchCategory(seacrhRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

    
    @Test(expected = NoContentFoundException.class)
    public void fetchActiveCategoryByIdl_ThrowsException() {
        categoryRepository.fetchCategoryDetails(0L);
    }

    @Test
    public void fetchActiveCategoryById() {
        Category category=categoryRepository
                .fetchActiveCategoryEntityById(1L);

        assertNotNull(category);
    }
}
