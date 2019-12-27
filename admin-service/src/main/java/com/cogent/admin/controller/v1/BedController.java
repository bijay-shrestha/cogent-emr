package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.bed.BedRequestDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.admin.service.BedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.BedConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.BedConstants.BASE_BED;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 10/2/19
 */

@RestController
@RequestMapping(API_V1 + BASE_BED)
@Api(value = BASE_BED_API_VALUE)
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PostMapping
    @ApiOperation(SAVE_BED_OPERATION)
    public ResponseEntity<?> saveBed(@Valid @RequestBody BedRequestDTO requestDTO) {
        bedService.createBed(requestDTO);
        return created(URI.create(API_V1 + BASE_BED)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_BED_OPERATION)
    public ResponseEntity<?> deleteBed(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        bedService.deleteBed(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_BED_OPERATION)
    public ResponseEntity<?> updateBed(@Valid @RequestBody BedUpdateRequestDTO updateRequestDTO) {
        bedService.updateBed(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_BED_OPERATION)
    public ResponseEntity<?> searchBed(@RequestBody BedSearchRequestDTO searchRequestDTO,
                                       @RequestParam("page") int page,
                                       @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(bedService.searchBed(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(BED_DETAIL_OPERATION)
    public ResponseEntity<?> fetchBedDetails(@PathVariable Long id) {
        return ok(bedService.fetchBedDetails(id));
    }


    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_BED_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(bedService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_BED_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(bedService.fetchActiveDropDownList());
    }
}
