package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;
import com.cogent.admin.service.SpecializationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.SpecializationConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.SpecializationConstants.BASE_SPECIALIZATION;
import static com.cogent.admin.constants.WebResourceKeyConstants.SpecializationConstants.DOCTOR_ID_PATH_VARIABLE_BASE;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-09-25
 */
@RestController
@RequestMapping(API_V1 + BASE_SPECIALIZATION)
@Api(BASE_API_VALUE)
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody SpecializationRequestDTO requestDTO) {
        specializationService.save(requestDTO);
        return created(create(API_V1 + BASE_SPECIALIZATION)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody SpecializationUpdateRequestDTO updateRequestDTO) {
        specializationService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        specializationService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody SpecializationSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(specializationService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchActiveSpecializationForDropDown() {
        return ok(specializationService.fetchActiveSpecializationForDropDown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(specializationService.fetchDetailsById(id));
    }

    @GetMapping(DOCTOR_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchSpecializationByDoctorId(@PathVariable("DoctorId") Long DoctorId) {
        return ok(specializationService.fetchSpecializationByDoctorId(DoctorId));
    }
}
