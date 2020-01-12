package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;
import com.cogent.admin.service.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.HospitalConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.HospitalConstants.BASE_HOSPITAL;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Rupak
 */
@RestController
@RequestMapping(API_V1 + BASE_HOSPITAL)
@Api(BASE_API_VALUE)
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody HospitalRequestDTO requestDTO) {
        hospitalService.save(requestDTO);
        return ResponseEntity.created(create(API_V1 + BASE_HOSPITAL)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody HospitalUpdateRequestDTO updateRequestDTO) {
        hospitalService.updateHospital(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        hospitalService.deleteHospital(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody HospitalSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(hospitalService.searchHospital(searchRequestDTO, pageable));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(ENTITY_OPERATION)
    public ResponseEntity<?> fetchHospital(@PathVariable Long id) {
        return ok(hospitalService.fetchHospital(id));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchHospitalForDropDown() {
        return ok(hospitalService.fetchHospitalForDropDown());
    }



}
