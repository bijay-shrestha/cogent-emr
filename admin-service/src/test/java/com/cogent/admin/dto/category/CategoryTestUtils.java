package com.cogent.admin.dto.category;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.category.CategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.persistence.model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 10/24/19
 */
public class CategoryTestUtils {
    public static CategoryRequestDTO getCategoryRequestDTO() {
        return CategoryRequestDTO.builder()
                .name("Category-B")
                .code("CT-B")
                .status('Y')
                .build();
    }

    public static CategoryUpdateRequestDTO getCategoryUpdateRequestDTO() {
        return CategoryUpdateRequestDTO.builder()
                .id(1L)
                .name("Category-B")
                .code("CT-B")
                .status('Y')
                .remarks("I want to update re")
                .build();
    }

    public static CategoryUpdateRequestDTO getCategoryUpdateRequestDTOForBadRequest() {
        return CategoryUpdateRequestDTO.builder()
                .id(1L)
                .name("Category-B")
                .code("CT-B")
                .status('D')
                .remarks("I want to update re")
                .build();
    }

    public static CategorySearchRequestDTO getCategorySearchRequestDTO() {
        return CategorySearchRequestDTO.builder()
                .id(1L)
                .name("Category-B")
                .code("CT-B")
                .status('Y')
                .build();
    }

    public static List<Object[]> getCategoryObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Category-B";
        object[1] = "CT-B";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getCategoryObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "CT-B";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getCategoryObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Category-B";
        objects.add(object);
        return objects;
    }


    public static CategoryMinimalResponseDTO getCategoryMinimalResponseDTO() {
        return CategoryMinimalResponseDTO.builder()
                .id(1L)
                .name("Category-B")
                .code("CT-B")
                .status('Y')
                .build();
    }

    public static CategoryResponseDTO getCategoryResponseDTO() {
        return CategoryResponseDTO.builder()
                .remarks("test")
                .build();
    }

    public static Category getCategoryInfo() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category-B");
        category.setCode("CT-B");
        category.setStatus('Y');

        return category;
    }

    public static List<DropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Category-B")
                .build());

    }

    public static List<CategoryMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getCategoryMinimalResponseDTO());
    }

    public static DeleteRequestDTO getDeleteReuqestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("deleteDrug").build();
    }
}
