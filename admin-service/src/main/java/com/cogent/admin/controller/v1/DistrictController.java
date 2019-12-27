package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.district.DistrictRequestDTO;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.request.district.DistrictUpdateRequestDTO;
import com.cogent.admin.service.DistrictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.DistrictConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DistrictConstants.BASE_DISTRICT;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1 + BASE_DISTRICT)
@Api(value = BASE_DISTRICT_API_VALUE)
public class DistrictController {
    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping
    @ApiOperation(SAVE_DISTRICT_OPERATION)
    public ResponseEntity<?> saveDistrict(@Valid @RequestBody DistrictRequestDTO districtRequestDTO) {
        districtService.createDistrict(districtRequestDTO);
        return created(URI.create(API_V1 + BASE_DISTRICT)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_DISTRICT_OPERATION)
    public ResponseEntity<?> deleteDistrict(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        districtService.deleteDistrict(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_DISTRICT_OPERATION)
    public ResponseEntity<?> updateDistrict(@Valid @RequestBody DistrictUpdateRequestDTO updateRequestDTO) {
        districtService.updateDistrict(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> searchDistrict(@RequestBody DistrictSearchRequestDTO searchRequestDTO,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(districtService.searchDistrict(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DISTRICT_DETAIL_OPERATION)
    public ResponseEntity<?> fetchDistrictDetails(@PathVariable Long id) {
        return ok(districtService.fetchDistrictDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_DISTRICT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(districtService.districtDropdown());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_DISTRICT_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(districtService.activeDistrictDropdown());
    }


}
