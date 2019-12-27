package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;
import com.cogent.admin.service.MaritalStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.MaritalStatusConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.MaritalStatusConstants.BASE_MARITAL_STATUS;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1 + BASE_MARITAL_STATUS)
@Api(BASE_API_VALUE)
public class MaritalStatusController {

    private final MaritalStatusService maritalStatusService;

    public MaritalStatusController(MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody MaritalStatusRequestDTO requestDTO) {
        maritalStatusService.save(requestDTO);
        return ResponseEntity.created(URI.create(API_V1 + BASE_MARITAL_STATUS + SAVE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody MaritalStatusUpdateRequestDTO updateRequestDTO) {
        maritalStatusService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        maritalStatusService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody MaritalStatusSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(maritalStatusService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchMaritalStatusForDropDown() {
        return ok(maritalStatusService.fetchMaritalStatusForDropDown());
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_MARITAL_OPERATION)
    public ResponseEntity<?> fetchMartialStatus(@PathVariable Long id) {
        return ok(maritalStatusService.fetchMaritalStatus(id));
    }


}
