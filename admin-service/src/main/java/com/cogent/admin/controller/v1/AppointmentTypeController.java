package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;
import com.cogent.admin.service.AppointmentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.AppointmentTypeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AppointmentTypeConstants.BASE_APPOINTMENT_TYPE;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-09-26
 */
@RestController
@RequestMapping(API_V1 + BASE_APPOINTMENT_TYPE)
@Api(BASE_API_VALUE)
public class AppointmentTypeController {
    private final AppointmentTypeService appointmentTypeService;

    public AppointmentTypeController(AppointmentTypeService appointmentTypeService) {
        this.appointmentTypeService = appointmentTypeService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody AppointmentTypeRequestDTO requestDTO) {
        appointmentTypeService.save(requestDTO);
        return created(create(API_V1 + BASE_APPOINTMENT_TYPE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody AppointmentTypeUpdateRequestDTO updateRequestDTO) {
        appointmentTypeService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        appointmentTypeService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody AppointmentTypeSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(appointmentTypeService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchAppointmentTypeForDropdown() {
        return ok(appointmentTypeService.fetchAppointmentTypeForDropdown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(appointmentTypeService.fetchDetailsById(id));
    }
}