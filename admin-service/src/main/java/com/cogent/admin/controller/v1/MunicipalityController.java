package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityDropdownDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.admin.service.MunicipalityService;
import com.cogent.persistence.model.Municipality;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.cogent.admin.constants.SwaggerConstants.MunicipalityConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.MunicipalityConstants.BASE_MUNICIPALITY;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-09-15
 */
@RestController
@RequestMapping(API_V1 + BASE_MUNICIPALITY)
@Api(BASE_API_VALUE)
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody MunicipalityRequestDTO requestDTO) {
        municipalityService.save(requestDTO);
        return created(create(API_V1 + BASE_MUNICIPALITY + SAVE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody MunicipalityUpdateRequestDTO updateRequestDTO) {
        municipalityService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        municipalityService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<List<MunicipalityMinimalResponseDTO>> search(
            @RequestBody MunicipalitySearchRequestDTO searchRequestDTO,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok().body(municipalityService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<MunicipalityResponseDTO> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(municipalityService.fetchDetailsById(id));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public Municipality fetchMunicipalityById(@PathVariable("id") Long id) {
        return municipalityService.fetchMunicipalityById(id);
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<List<MunicipalityDropdownDTO>> fetchAdminCategoryForDropDown() {
        return ok(municipalityService.fetchActiveMunicipalityForDropDown());
    }


}
