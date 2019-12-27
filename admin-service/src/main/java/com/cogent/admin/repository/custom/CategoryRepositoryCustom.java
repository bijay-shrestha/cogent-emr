package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.response.category.CategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.persistence.model.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author Sauravi  on 2019-08-26
 */

@Repository
@Qualifier("categoryRepositoryCustom")
public interface CategoryRepositoryCustom {

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();

    List<CategoryMinimalResponseDTO> searchCategory(
            CategorySearchRequestDTO searchRequestDTO, Pageable pageable);

    List<Object[]> fetchCategoryByNameOrCode(String name, String code);

    List<Object[]> checkCategoryNameAndCodeIfExist(Long id,String name, String code);

    CategoryResponseDTO fetchCategoryDetails(Long id);

    Category fetchActiveCategoryEntityById(Long id);

}
