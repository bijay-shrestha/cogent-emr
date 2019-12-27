package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeUpdateRequestDTO;
import com.cogent.admin.service.PatientTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.PatientTypeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.PatientTypeConstants.BASE_PATIENT_TYPE;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-09-26
 */
@RestController
@RequestMapping(API_V1 + BASE_PATIENT_TYPE)
@Api(BASE_API_VALUE)
public class PatientTypeController {
    private final PatientTypeService patientTypeService;

    public PatientTypeController(PatientTypeService patientTypeService) {
        this.patientTypeService = patientTypeService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody PatientTypeRequestDTO requestDTO) {
        patientTypeService.save(requestDTO);
        return created(create(API_V1 + BASE_PATIENT_TYPE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody PatientTypeUpdateRequestDTO updateRequestDTO) {
        patientTypeService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        patientTypeService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody PatientTypeSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(patientTypeService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchActivePatientType() {
        return ok(patientTypeService.fetchActivePatientTypeForDropdown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(patientTypeService.fetchDetailsById(id));
    }
}
