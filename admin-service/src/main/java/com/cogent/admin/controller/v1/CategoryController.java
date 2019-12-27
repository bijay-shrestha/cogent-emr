package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.admin.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.CategoryConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.CategoryConstants.BASE_CATEGORY;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 10/24/19
 */

@RestController
@RequestMapping(API_V1 + BASE_CATEGORY)
@Api(value = BASE_API_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ApiOperation(SAVE_CATEGORY_OPERATION)
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryRequestDTO requestDTO) {
        categoryService.createCategory(requestDTO);
        return created(URI.create(API_V1 + BASE_CATEGORY)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_CATEGORY_OPERATION)
    public ResponseEntity<?> deleteCategory(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        categoryService.deleteCategory(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_CATEGORY_OPERATION)
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryUpdateRequestDTO updateRequestDTO) {
        categoryService.updateCategory(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_CATEGORY_OPERATION)
    public ResponseEntity<?> searchCategory(@RequestBody CategorySearchRequestDTO searchRequestDTO,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(categoryService.searchCategory(searchRequestDTO, pageable));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(CATEGORY_OPERATION)
    public ResponseEntity<?> fetchEthnicity(@PathVariable Long id) {
        return ok(categoryService.fetchActiveCategoryEntity(id));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(CATEGORY_DETAIL_OPERATION)
    public ResponseEntity<?> fetchCategoryDetails(@PathVariable Long id) {
        return ok(categoryService.fetchCategoryDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_FOR_CATEGORY_DROPDOWN)
    public ResponseEntity<?> dropDown() {
        return ok(categoryService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_FOR_CATEGORY_ACTIVE_DROPDOWN)
    public ResponseEntity<?> activeDropDown() {
        return ok(categoryService.fetchActiveDropDownList());
    }
}
