package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityUpdateRequestDTO;
import com.cogent.admin.service.EthnicityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.EthnicityConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.EthnicityConstants.BASE_ETHNICITY;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi  on 2019-08-25
 */

@RestController
@RequestMapping(API_V1 + BASE_ETHNICITY)
@Api(value = BASE_ETHNICITY_API_VALUE)
public class EthnicityController {

    private final EthnicityService ethnicityService;

    public EthnicityController(EthnicityService ethnicityService) {
        this.ethnicityService = ethnicityService;
    }

    @PostMapping
    @ApiOperation(SAVE_ETHNICITY_OPERATION)
    public ResponseEntity<?> saveEthnicity(@Valid @RequestBody EthnicityRequestDTO ethnicityRequestDTO) {
        ethnicityService.createEthnicity(ethnicityRequestDTO);
        return created(URI.create(API_V1 + BASE_ETHNICITY)).build();
    }


    @DeleteMapping
    @ApiOperation(DELETE_ETHNICITY_OPERATION)
    public ResponseEntity<?> deleteEthnicity(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        ethnicityService.deleteEthnicity(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_ETHNICITY_OPERATION)
    public ResponseEntity<?> updateEthnicity(@Valid @RequestBody EthnicityUpdateRequestDTO ethnicityUpdateRequestDTO) {
        ethnicityService.updateEthnicity(ethnicityUpdateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> searchEthnicity(@RequestBody EthnicitySearchRequestDTO ethnicitySearchRequestDTO,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(ethnicityService.searchEthnicity(ethnicitySearchRequestDTO, pageable));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(ETHNICITY_OPERATION)
    public ResponseEntity<?> fetchEthnicity(@PathVariable Long id) {
        return ok(ethnicityService.fetchEthnicity(id));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(ETHNICITY_DETAIL_OPERATION)
    public ResponseEntity<?> fetchEthnicityDetails(@PathVariable Long id) {
        return ok(ethnicityService.fetchEthnicityDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_ETHNICITY_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(ethnicityService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_ETHNICITY_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(ethnicityService.fetchActiveDropDownList());
    }


}
