package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.admin.service.impl.ServiceChargeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.ServiceChargeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ServiceChargeConstants.BASE_SERVICE_CHARGE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 11/18/19
 */

@RestController
@RequestMapping(API_V1 + BASE_SERVICE_CHARGE)
@Api(BASE_SERVICE_CHARGE_API_VALUE)
public class ServiceChargeController {

    private final ServiceChargeServiceImpl service;

    public ServiceChargeController(ServiceChargeServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation(SAVE_SERVICE_CHARGE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ServiceChargeRequestDTO requestDTO) {
        service.createServiceCharge(requestDTO);
        return created(URI.create(API_V1 + BASE_SERVICE_CHARGE)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_SERVICE_CHARGE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteServiceCharge(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_SERVICE_CHARGE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody ServiceChargeUpdateRequestDTO requestDTO) {
        service.updateServiceCharge(requestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody ServiceChargeSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(service.searchServiceCharge(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(SERVICE_CHARGE_DETAIL_OPERATION)
    public ResponseEntity<?> fetchDetails(@PathVariable Long id) {
        return ok(service.fetchServiceChargeDetailsById(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_SERVICE_CHARGE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(service.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_SERVICE_CHARGE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(service.fetchActiveDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE + BILLING_MODE_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_ACTIVE_SERVICE_CHARGE_BY_BILLING_MODE_ID_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDownByBillingModeId(@PathVariable Long billingModeId) {
        return ok(service.fetchActiveDropDownListByBillingModeId(billingModeId));
    }
}
