package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.admin.service.AssignBedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.AssignBedConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AssignBedConstants.BASE_ASSIGN_BED;
import static com.cogent.admin.constants.WebResourceKeyConstants.WardConstants.WARD_ID_PATH_VARIABLE_BASE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 11/7/19
 */

@RestController
@RequestMapping(API_V1 + BASE_ASSIGN_BED)
@Api(value = BASE_ASSIGN_BED_API_VALUE)
public class AssignBedController {

    private final AssignBedService assignBedService;

    public AssignBedController(AssignBedService assignBedService) {
        this.assignBedService = assignBedService;
    }

    @PostMapping
    @ApiOperation(SAVE_ASSIGN_BED_OPERATION)
    public ResponseEntity<?> saveBed(@Valid @RequestBody AssignBedRequestDTO requestDTO) {
        assignBedService.createAssignBed(requestDTO);
        return created(URI.create(API_V1 + BASE_ASSIGN_BED)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_ASSIGN_BED_OPERATION)
    public ResponseEntity<?> deleteBed(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        assignBedService.deleteAssignedBed(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_ASSIGN_BED_OPERATION)
    public ResponseEntity<?> updateBed(@Valid @RequestBody AssignBedUpdateRequestDTO updateRequestDTO) {
        assignBedService.updateAssignedBed(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_ASSIGN_BED_OPERATION)
    public ResponseEntity<?> searchBed(@RequestBody AssignBedSearchRequestDTO searchRequestDTO,
                                       @RequestParam("page") int page,
                                       @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(assignBedService.searchAssignedBed(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(ASSIGN_BED_DETAIL_OPERATION)
    public ResponseEntity<?> fetchBedDetails(@PathVariable Long id) {
        return ok(assignBedService.fetchAssignedBedDetails(id));
    }

    @GetMapping(DROPDOWN + WARD_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_ASSIGN_BED_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown(@PathVariable Long wardId) {
        return ok(assignBedService.fetchDropDownList(wardId));
    }

    @GetMapping(DROPDOWN + ACTIVE + WARD_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_ACTIVE_ASSIGN_BED_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown(@PathVariable Long wardId) {
        return ok(assignBedService.fetchActiveDropDownList(wardId));
    }

}
