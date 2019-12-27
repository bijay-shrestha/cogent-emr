package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryDropdownDTO;
import com.cogent.admin.service.AdminCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.cogent.admin.constants.SwaggerConstants.AdminCategoryConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AdminCategoryConstants.BASE_ADMIN_CATEGORY;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-08-11
 */
@RestController
@RequestMapping(API_V1 + BASE_ADMIN_CATEGORY)
@Api(BASE_API_VALUE)
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody AdminCategoryRequestDTO requestDTO) {
        adminCategoryService.save(requestDTO);
        return created(create(API_V1 + BASE_ADMIN_CATEGORY)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody AdminCategoryUpdateRequestDTO updateRequestDTO) {
        adminCategoryService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        adminCategoryService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody AdminCategorySearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(adminCategoryService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<List<AdminCategoryDropdownDTO>> fetchAdminCategoryForDropDown() {
        return ok(adminCategoryService.fetchActiveAdminCategoryForDropDown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(adminCategoryService.fetchDetailsById(id));
    }

    @GetMapping(EXCEL)
    @ApiOperation(DOWNLOAD_EXCEL_OPERATION)
    public ResponseEntity<?> downloadExcel(HttpServletResponse response) throws IOException {
        adminCategoryService.createExcelInByteArray(response);
        return ok().build();
    }
}
