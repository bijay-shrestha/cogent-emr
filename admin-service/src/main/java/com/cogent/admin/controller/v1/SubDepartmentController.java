package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentUpdateRequestDTO;
import com.cogent.admin.service.SubDepartmentService;
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

import static com.cogent.admin.constants.SwaggerConstants.SubDepartmentConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.SubDepartmentConstants.BASE_SUB_DEPARTMENT;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1 + BASE_SUB_DEPARTMENT)
@Api(value = BASE_SUB_DEPARTMENT_API_VALUE)
public class SubDepartmentController {

    private final SubDepartmentService subDepartmentService;

    public SubDepartmentController(SubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
    }

    @PostMapping
    @ApiOperation(SAVE_SUB_DEPARTMENT_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody SubDepartmentRequestDTO subDepartmentRequestDTO) {
        subDepartmentService.createSubDepartment(subDepartmentRequestDTO);
        return created(URI.create(API_V1 + BASE_SUB_DEPARTMENT + SAVE)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_SUB_DEPARTEMNT_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        subDepartmentService.deleteSubDepartment(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_SUB_DEPARTMENT_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO) {
        subDepartmentService.updateSubDepartment(subDepartmentUpdateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_SUB_DEPARTMENT_OPERATION)
    public ResponseEntity<?> search(@RequestBody SubDepartmentSearchRequestDTO subDepartmentSearchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok(subDepartmentService.searchSubDepartment(subDepartmentSearchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(SUB_DEPARTMENT_DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetails(@PathVariable Long id) {
        return ok(subDepartmentService.fetchSubDepartmentDetailsById(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_SUB_DEPARTMENT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDownList() {
        return ok(subDepartmentService.dropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_SUB_DEPARTMENT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDownList() {
        return ok(subDepartmentService.activeDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE + DEPARTMENT_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_SUB_DEPARTMENT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDownListByDepartmentId(@PathVariable Long departmentId) {
        return ok(subDepartmentService.activeDropDownListByDepartmentId(departmentId));
    }

    @GetMapping(EXCEL)
    @ApiOperation(DOWNLOAD_SUB_DEPARTMENT_EXCEL_OPERATION)
    public ResponseEntity<?> downloadExcel(HttpServletResponse response) throws IOException {
        subDepartmentService.createExcelInByteArray(response);
        return ok().build();
    }
}
