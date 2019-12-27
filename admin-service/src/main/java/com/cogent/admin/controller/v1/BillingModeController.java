package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeUpdateRequestDTO;
import com.cogent.admin.service.BillingModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.BillingModeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.BillingModeConstants.BASE_BILLING_MODE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 12/4/19
 */

@RestController
@RequestMapping(API_V1 + BASE_BILLING_MODE)
@Api(BASE_BILLING_MODE_API_VALUE)
public class BillingModeController {

    private final BillingModeService service;

    public BillingModeController(BillingModeService service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation(SAVE_BILLING_MODE_OPERATION)
    public ResponseEntity<?> saveBillingMode(@Valid @RequestBody BillingModeRequestDTO requestDTO) {
        service.createBillingMode(requestDTO);
        return created(URI.create(API_V1 + BASE_BILLING_MODE)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_BILLING_MODE_OPERATION)
    public ResponseEntity<?> deleteBillingMode(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteBillingMode(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_BILLING_MODE_OPERATION)
    public ResponseEntity<?> updateBillingMode(@Valid @RequestBody BillingModeUpdateRequestDTO updateRequestDTO) {
        service.updateBillingMode(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_BILLING_MODE_OPERATION)
    public ResponseEntity<?> searchBillingMode(@RequestBody BillingModeSearchRequestDTO searchRequestDTO,
                                       @RequestParam("page") int page,
                                       @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(service.searchBillingMode(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(BILLING_MODE_DETAIL_OPERATION)
    public ResponseEntity<?> fetchBillingModeDetails(@PathVariable Long id) {
        return ok(service.fetchBillingModeDetails(id));
    }


    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_BILLING_MODE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(service.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_BILLING_MODE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(service.fetchActiveDropDownList());
    }
}

