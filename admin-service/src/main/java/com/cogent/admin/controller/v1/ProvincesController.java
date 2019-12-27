package com.cogent.admin.controller.v1;


import com.cogent.admin.constants.SwaggerConstants;
import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.admin.service.ProvincesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.ProvincesConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ProvincesConstants.BASE_PROVINCES;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1 + BASE_PROVINCES)
@Api(value = SwaggerConstants.ProvincesConstant.BASE_PROVINCES_API_VALUE)
public class ProvincesController {

    private final ProvincesService provincesService;

    public ProvincesController(ProvincesService provincesService) {
        this.provincesService = provincesService;
    }

    @PostMapping
    @ApiOperation(SAVE_PROVINCES_OPERATION)
    public ResponseEntity<?> saveProvinces(@Valid @RequestBody ProvincesRequestDTO provincesRequestDTO) {
        provincesService.createProvinces(provincesRequestDTO);
        return created(URI.create(API_V1 + BASE_PROVINCES)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_PROVINCES_OPERATION)
    public ResponseEntity<?> deleteProvinces(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        provincesService.deleteProvinces(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_PROVINCES_OPERATION)
    public ResponseEntity<?> updateProvinces(@Valid @RequestBody ProvincesUpdateRequestDTO provincesUpdateRequestDTO) {
        provincesService.updateProvinces(provincesUpdateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> searchProvinces(@RequestBody ProvincesSearchRequestDTO searchRequestDTO,
                                             @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok(provincesService.searchProvinces(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(PROVINCES_DETAIL_OPERATION)
    public ResponseEntity<?> fetchProvincesDetails(@PathVariable Long id) {
        return ok(provincesService.fetchProvincesDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_PROVINCES_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(provincesService.provincesDropdown());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_PROVINCES_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(provincesService.activeProvinceDropdown());
    }
}

