package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.category.CategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.persistence.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sauravi Thapa 10/24/19
 */

@Service
public interface CategoryService {
    void createCategory(CategoryRequestDTO requestDTO);

    void deleteCategory(DeleteRequestDTO deleteRequestDTO);

    void updateCategory(CategoryUpdateRequestDTO updateRequestDTO);

    CategoryResponseDTO fetchCategoryDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();

    List<CategoryMinimalResponseDTO> searchCategory(
            CategorySearchRequestDTO searchRequestDTO,
            Pageable pageable);

    Category fetchActiveCategoryEntity(Long id);
}
