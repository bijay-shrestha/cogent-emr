package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;
import com.cogent.admin.service.AppointmentModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.AppointmentModeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AppointmentModeConstants.BASE_APPOINTMENT_MODE;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-10-10
 */
@RestController
@RequestMapping(API_V1 + BASE_APPOINTMENT_MODE)
@Api(BASE_API_VALUE)
public class AppointmentModeController {

    private final AppointmentModeService appointmentModeService;

    public AppointmentModeController(AppointmentModeService appointmentModeService) {
        this.appointmentModeService = appointmentModeService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody AppointmentModeRequestDTO requestDTO) {
        appointmentModeService.save(requestDTO);
        return created(create(API_V1 + BASE_APPOINTMENT_MODE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody AppointmentModeUpdateRequestDTO updateRequestDTO) {
        appointmentModeService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        appointmentModeService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody AppointmentModeSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(appointmentModeService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchActiveAppointmentMode() {
        return ok(appointmentModeService.fetchAppointmentModeForDropdown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(appointmentModeService.fetchDetailsById(id));
    }
}
