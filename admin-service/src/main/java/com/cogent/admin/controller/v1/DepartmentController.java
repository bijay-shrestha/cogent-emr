package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.DepartmentConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DepartmentConstants.BASE_DEPARTMENT;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi
 */

@RestController
@RequestMapping(API_V1 + BASE_DEPARTMENT)
@Api(value = BASE_DEPARTMENT_API_VALUE)
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ApiOperation(SAVE_DEPARTMENT_OPERATION)
    public ResponseEntity<?> saveDepartment(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        departmentService.createDepartment(departmentRequestDTO);
        return created(URI.create(API_V1 + BASE_DEPARTMENT)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_DEPARTMENT_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        departmentService.deleteDepartment(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_DEPARTMENT_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody DepartmentUpdateRequestDTO departmentUpdateRequestDTO) {
        departmentService.updateDepartment(departmentUpdateRequestDTO);
        return ok().build();
    }


    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_DEPARTMENT_OPERATION)
    public ResponseEntity<?> search(@RequestBody DepartmentSearchRequestDTO departmentSearchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(departmentService.searchDepartment(departmentSearchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DEPARTMENT_DETAILS_OPERATION)
    public ResponseEntity<?> fetchDepartmentDetails(@PathVariable Long id) {
        return ok(departmentService.fetchDepartmentDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_DEPARTMENT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> fetchDropDownList() {
        return ok(departmentService.dropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_DEPARTMENT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> fetchActiveDropDownList() {
        return ok(departmentService.activeDropDownList());
    }

    @GetMapping(EXCEL)
    @ApiOperation(DOWNLOAD_DEPARTMENT_EXCEL_OPERATION)
    public ResponseEntity<?> downloadExcel(HttpServletResponse response) throws IOException {
        departmentService.createExcelInByteArray(response);
        return ok().build();
    }
}
