package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;
import com.cogent.admin.service.ReferrerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.ReferrerConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ReferrerConstants.BASE_REFERRER;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Rupak
 */

@RestController
@RequestMapping(API_V1 + BASE_REFERRER)
@Api(BASE_API_VALUE)
public class ReferrerController {

    private final ReferrerService referrerService;

    public ReferrerController(ReferrerService referrerService) {
        this.referrerService = referrerService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ReferrerRequestDTO requestDTO) {
        referrerService.save(requestDTO);
        return ResponseEntity.created(create(API_V1 + BASE_REFERRER + SAVE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ReferrerUpdateRequestDTO updateRequestDTO) {
        referrerService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        referrerService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody ReferrerSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(referrerService.search(searchRequestDTO, pageable));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(ENTITY_OPERATION)
    public ResponseEntity<?> fetchInsuranceCompany(@PathVariable Long id) {
        return ok(referrerService.fetchReferrer(id));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchReferrerForDropDown() {
        return ok(referrerService.fetchReferrerForDropDown());
    }

}
