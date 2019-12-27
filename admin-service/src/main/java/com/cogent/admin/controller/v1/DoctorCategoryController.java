package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.admin.service.impl.DoctorCategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.DoctorCategoryConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorCategoryConstants.BASE_DOCTOR_CATEGORY;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 11/21/19
 */

@RestController
@RequestMapping(API_V1+BASE_DOCTOR_CATEGORY)
@Api(value = BASE_DOCTOR_CATEGORY_API_VALUE)
public class DoctorCategoryController {

    private final DoctorCategoryServiceImpl service;

    public DoctorCategoryController(DoctorCategoryServiceImpl service) {
        this.service = service;
    }


    @PostMapping
    @ApiOperation(SAVE_DOCTOR_CATEGORY_OPERATION)
    public ResponseEntity<?> saveDrug(@Valid @RequestBody DoctorCategoryRequestDTO requestDTO) {
        service.createDoctorCategory(requestDTO);
        return created(URI.create(API_V1 + BASE_DOCTOR_CATEGORY)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_DOCTOR_CATEGORY_OPERATION)
    public ResponseEntity<?> deleteDrug(@Valid @RequestBody DeleteRequestDTO requestDTO) {
        service.deleteDoctorCategory(requestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_DOCTOR_CATEGORY_OPERATION)
    public ResponseEntity<?> updateDrug(@Valid @RequestBody DoctorCategoryUpdateRequestDTO
                                                requestDTO) {
        service.updateDoctorCategory(requestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_DOCTOR_CATEGORY_OPERATION)
    public ResponseEntity<?> searchDrug(@RequestBody DoctorCategorySearchRequestDTO requestDTO,
                                        @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok(service.searchDoctorCategory(requestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DOCTOR_CATEGORY_DETAIL_OPERATION)
    public ResponseEntity<?> fetchDrugDetails(@PathVariable Long id) {
        return ok(service.fetchDoctorCategoryDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_DOCTOR_CATEGORY_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(service.doctorCategoryDropdown());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_DOCTOR_CATEGORY_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(service.activeDoctorCategoryDropdown());
    }
}
