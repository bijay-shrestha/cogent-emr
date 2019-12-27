package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.persistence.model.Category;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 10/24/19
 */
public class CategoryUtils {
    public static Category convertToCategoryInfo(CategoryRequestDTO requestDTO) {
        requestDTO.setName(toUpperCase(requestDTO.getName()));
        requestDTO.setCode(toUpperCase(requestDTO.getCode()));
        return MapperUtility.map(requestDTO, Category.class);
    }

    public static BiFunction<Category, DeleteRequestDTO, Category> delete = (categoryToUpdate, deleteRequestDTO) -> {
        categoryToUpdate.setStatus(deleteRequestDTO.getStatus());
        categoryToUpdate.setRemarks(deleteRequestDTO.getRemarks());

        return categoryToUpdate;
    };

    public static Category update(Category categoryToUpdate, CategoryUpdateRequestDTO updateRequestDTO) {
        categoryToUpdate.setName(toUpperCase(updateRequestDTO.getName()));
        categoryToUpdate.setCode(toUpperCase(updateRequestDTO.getCode()));
        categoryToUpdate.setStatus(updateRequestDTO.getStatus());
        categoryToUpdate.setRemarks(updateRequestDTO.getRemarks());

        return categoryToUpdate;
    }

    ;
}
