package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.admin.service.impl.ServiceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.ServiceConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ServiceConstants.BASE_SERVICE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 11/18/19
 */
@RestController
@RequestMapping(API_V1 + BASE_SERVICE)
@Api(value = BASE_SERVICE_API_VALUE)
public class ServiceController {

    private final ServiceServiceImpl service;

    public ServiceController(ServiceServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation(SAVE_SERVICE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ServiceRequestDTO requestDTO) {
        service.createService(requestDTO);
        return created(URI.create(API_V1 + BASE_SERVICE)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_SERVICE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteService(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_SERVICE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody ServiceUpdateRequestDTO requestDTO) {
        service.updateService(requestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody ServiceSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(service.searchService(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(SERVICE_DETAIL_OPERATION)
    public ResponseEntity<?> fetchDetails(@PathVariable Long id) {
        return ok(service.fetchServiceDetails(id));
    }

    @GetMapping(DROPDOWN + DEPARTMENT_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_SERVICE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown(@PathVariable Long departmentId) {
        return ok(service.fetchDropDownList(departmentId));
    }

    @GetMapping(DROPDOWN + ACTIVE + DEPARTMENT_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_ACTIVE_SERVICE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown(@PathVariable Long departmentId) {
        return ok(service.fetchActiveDropDownList(departmentId));
    }
}
