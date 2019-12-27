package com.cogent.admin.controller.v1;


import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyUpdateRequestDTO;
import com.cogent.admin.service.InsuranceCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.InsuranceCompanyConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.InsuranceCompanyConstants.BASE_INSURANCE_COMPANY;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping(API_V1 + BASE_INSURANCE_COMPANY)
@Api(value = BASE_API_VALUE)
public class InsuranceCompanyController {

    private InsuranceCompanyService insuranceCompanyService;

    public InsuranceCompanyController(InsuranceCompanyService insuranceCompanyService) {
        this.insuranceCompanyService = insuranceCompanyService;
    }

    @PostMapping
    @ApiOperation(SAVE_INSURANCE_COMPANY_OPERATION)
    public ResponseEntity<?> saveInsuranceCompany(@Valid @RequestBody InsuranceCompanyRequestDTO requestDTO) {
        insuranceCompanyService.createInsuranceCompany(requestDTO);
        return created(URI.create(API_V1 + BASE_INSURANCE_COMPANY)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_INSURANCE_COMPANY_OPERATION)
    public ResponseEntity<?> deleteInsuranceCompany(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        insuranceCompanyService.deleteInsuranceCompany(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_INSURANCE_COMPANY_OPERATION)
    public ResponseEntity<?> updateInsuranceCompany(@Valid @RequestBody InsuranceCompanyUpdateRequestDTO
                                                            updateRequestDTO) {
        insuranceCompanyService.updateInsuranceCompany(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_INSURANCE_COMPANY_OPERATION)
    public ResponseEntity<?> searchInsuranceCompany(@RequestBody InsuranceCompanySearchRequestDTO searchRequestDTO,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(insuranceCompanyService.searchInsuranceCompany(searchRequestDTO, pageable));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(INSURANCE_COMPANY_OPERATION)
    public ResponseEntity<?> fetchInsuranceCompany(@PathVariable Long id) {
        return ok(insuranceCompanyService.fetchInsuranceCompany(id));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(INSURANCE_COMPANY_DETAIL_OPERATION)
    public ResponseEntity<?> fetchInsuranceCompanyDetails(@PathVariable Long id) {
        return ok(insuranceCompanyService.fetchInsuranceCompanyDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_FOR_INSURANCE_COMPANY_DROPDOWN)
    public ResponseEntity<?> dropDown() {
        return ok(insuranceCompanyService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_FOR_INSURANCE_COMPANY_ACTIVE_DROPDOWN)
    public ResponseEntity<?> activeDropDown() {
        return ok(insuranceCompanyService.fetchActiveDropDownList());
    }

}
