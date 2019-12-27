package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.unit.UnitRequestDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.admin.service.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.UnitConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.UnitConstants.BASE_UNIT;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 10/13/19
 */

@RestController
@RequestMapping(API_V1 + BASE_UNIT)
@Api(value = BASE_UNIT_API_VALUE)
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    @ApiOperation(SAVE_UNIT_OPERATION)
    public ResponseEntity<?> saveUnit(@Valid @RequestBody UnitRequestDTO requestDTO) {
        unitService.createUnit(requestDTO);
        return created(URI.create(API_V1 + BASE_UNIT)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_UNIT_OPERATION)
    public ResponseEntity<?> deleteUnit(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        unitService.deleteUnit(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_UNIT_OPERATION)
    public ResponseEntity<?> updateUnit(@Valid @RequestBody UnitUpdateRequestDTO updateRequestDTO) {
        unitService.updateUnit(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_UNIT_OPERATION)
    public ResponseEntity<?> searchUnit(@RequestBody UnitSearchRequestDTO searchRequestDTO,
                                        @RequestParam("page") int page,
                                        @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(unitService.searchUnit(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(UNIT_DETAIL_OPERATION)
    public ResponseEntity<?> fetchUnit(@PathVariable Long id) {
        return ok(unitService.fetchUnitDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_UNIT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(unitService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_UNIT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(unitService.fetchActiveDropDownList());
    }
}
