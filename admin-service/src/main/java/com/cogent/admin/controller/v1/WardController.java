package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ward.WardRequestDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.request.ward.WardUnitDeleteRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.service.WardService;
import com.cogent.admin.service.WardUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.WardConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.WardConstants.BASE_WARD;
import static com.cogent.admin.constants.WebResourceKeyConstants.WardUnitConstants.UNIT;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 10/2/19
 */

@RestController
@RequestMapping(API_V1 + BASE_WARD)
@Api(value = BASE_WARD_API_VALUE)
public class WardController {

    private final WardService wardService;

    private final WardUnitService wardUnitService;

    public WardController(WardService wardService, WardUnitService wardUnitService) {
        this.wardService = wardService;
        this.wardUnitService = wardUnitService;
    }

    @PostMapping
    @ApiOperation(SAVE_WARD_OPERATION)
    public ResponseEntity<?> saveWard(@Valid @RequestBody WardRequestDTO requestDTO) {
        wardService.createWard(requestDTO);
        return created(URI.create(API_V1 + BASE_WARD)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_WARD_OPERATION)
    public ResponseEntity<?> deleteWard(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        wardService.deleteWard(deleteRequestDTO);
        return ok().build();
    }

    @DeleteMapping(UNIT)
    @ApiOperation(DELETE_WARD_UNIT_OPERATION)
    public ResponseEntity<?> deleteWardUnit(@Valid @RequestBody WardUnitDeleteRequestDTO deleteRequestDTO) {
        wardUnitService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_WARD_OPERATION)
    public ResponseEntity<?> updateWard(@Valid @RequestBody WardUpdateRequestDTO updateRequestDTO) {
        wardService.updateWard(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_WARD_OPERATION)
    public ResponseEntity<?> searchWard(@RequestBody WardSearchRequestDTO searchRequestDTO,
                                        @RequestParam("page") int page,
                                        @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(wardService.searchWard(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(WARD_DETAIL_OPERATION)
    public ResponseEntity<?> fetchWard(@PathVariable Long id) {
        return ok(wardService.fetchWardDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_WARD_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(wardService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_WARD_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(wardService.fetchActiveDropDownList());
    }
}
