package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeUpdateRequestDTO;
import com.cogent.admin.service.DiscountSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.DiscountSchemeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DiscountSchemeConstants.*;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 11/11/19
 */

@RestController
@RequestMapping(API_V1 + BASE_DISCOUNT_SCHEME)
@Api(value = BASE_DISCOUNT_SCHEME_API_VALUE)
public class DiscountSchemeController {

    private final DiscountSchemeService service;

    public DiscountSchemeController(DiscountSchemeService discountSchemeService) {
        this.service = discountSchemeService;
    }

    @PostMapping
    @ApiOperation(SAVE_DISCOUNT_SCHEME_OPERATION)
    public ResponseEntity<?> saveDiscountScheme(@Valid @RequestBody DiscountSchemeRequestDTO requestDTO) {
        service.createDiscountScheme(requestDTO);
        return created(URI.create(API_V1 + BASE_DISCOUNT_SCHEME)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_DISCOUNT_SCHEME_OPERATION)
    public ResponseEntity<?> deleteDiscountScheme(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteDiscountScheme(deleteRequestDTO);
        return ok().build();
    }

    @DeleteMapping(DEPARTMENT_DISCOUNT)
    @ApiOperation(DELETE_DEPARTMENT_DISCOUNT_OPERATION)
    public ResponseEntity<?> deleteDepartmentDiscount(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteDepartmentDiscount(deleteRequestDTO);
        return ok().build();
    }

    @DeleteMapping(SERVICE_DISCOUNT)
    @ApiOperation(DELETE_SERVICE_DISCOUNT_OPERATION)
    public ResponseEntity<?> deleteServiceDiscount(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteServiceDiscount(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_DISCOUNT_SCHEME_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody DiscountSchemeUpdateRequestDTO updateRequestDTO) {
        service.updateDiscountScheme(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody DiscountSchemeSearchRequestDTO requestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(service.searchDiscountScheme(requestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DISCOUNT_SCHEME_DETAIL_OPERATION)
    public ResponseEntity<?> fetchBedDetails(@PathVariable Long id) {
        return ok(service.fetchDiscountSchemeDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_DISCOUNT_SCHEME_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(service.fetchDropDownListWithNetDiscount());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_DISCOUNT_SCHEME_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(service.fetchActiveDropDownListWithNetDiscount());
    }

    @GetMapping(DEPARTMENT_DISCOUNT + DROPDOWN + DEPARTMENT_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_DEPARTMENT_ID_OPERATION)
    public ResponseEntity<?> dropDownByDepartmentId(@PathVariable Long departmentId) {
        return ok(service.fetchDropDownListByDepartmentId(departmentId));
    }

    @GetMapping(DEPARTMENT_DISCOUNT + DROPDOWN + ACTIVE + DEPARTMENT_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_ACTIVE_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_DEPARTMENT_ID_OPERATION)
    public ResponseEntity<?> activeDropDownByDepartmentId(@PathVariable Long departmentId) {
        return ok(service.fetchActiveDropDownListByDepartmentId(departmentId));
    }

    @GetMapping(SERVICE_DISCOUNT + DROPDOWN + SERVICE_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_SERVICE_ID_OPERATION)
    public ResponseEntity<?> dropDownByServiceId(@PathVariable Long serviceId) {
        return ok(service.fetchDropDownListByServiceId(serviceId));
    }

    @GetMapping(SERVICE_DISCOUNT + DROPDOWN + ACTIVE + SERVICE_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_ACTIVE_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_SERVICE_ID_OPERATION)
    public ResponseEntity<?> activeDropDownByServiceId(@PathVariable Long serviceId) {
        return ok(service.fetchActiveDropDownListByServiceId(serviceId));
    }

}
