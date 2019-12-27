package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.request.drug.DrugUpdateRequestDTO;
import com.cogent.admin.service.DrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.DrugConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DrugConstants.BASE_DRUG;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1 + BASE_DRUG)
@Api(value = BASE_DRUG_API_VALUE)
public class DrugController {
    private DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @PostMapping
    @ApiOperation(SAVE_DRUG_OPERATION)
    public ResponseEntity<?> saveDrug(@Valid @RequestBody DrugRequestDTO drugRequestDTO) {
        drugService.createDrug(drugRequestDTO);
        return created(URI.create(API_V1 + BASE_DRUG)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_DRUG_OPERATION)
    public ResponseEntity<?> deleteDrug(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        drugService.deleteDrug(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_DRUG_OPERATION)
    public ResponseEntity<?> updateDrug(@Valid @RequestBody DrugUpdateRequestDTO
                                                       drugUpdateRequestDTO) {
        drugService.updateDrug(drugUpdateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> searchDrug(@RequestBody DrugSearchRequestDTO drugSearchRequestDTO,
                                               @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok(drugService.searchDrug(drugSearchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DRUG_DETAIL_OPERATION)
    public ResponseEntity<?> fetchDrugDetails(@PathVariable Long id) {
        return ok(drugService.fetchDrugDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_DRUG_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(drugService.drugDropdown());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_DRUG_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(drugService.activeDrugDropdown());
    }


}
