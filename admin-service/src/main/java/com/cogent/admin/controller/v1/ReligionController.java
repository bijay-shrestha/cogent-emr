package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionUpdateRequestDTO;
import com.cogent.admin.service.ReligionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.ReligionConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ReligionConstants.BASE_RELIGION;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(API_V1 + BASE_RELIGION)
@Api(BASE_API_VALUE)
public class ReligionController {

    private final ReligionService religionService;

    public ReligionController(ReligionService religionService) {
        this.religionService = religionService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ReligionRequestDTO requestDTO) {
        religionService.save(requestDTO);
        return ResponseEntity.created(create(API_V1 + BASE_RELIGION + SAVE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ReligionUpdateRequestDTO updateRequestDTO) {
        religionService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        religionService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody ReligionSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(religionService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchReligionForDropDown() {
        return ok(religionService.fetchReligionForDropDown());
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_RELIGION_OPERATION)
    public ResponseEntity<?> fetchMartialStatus(@PathVariable Long id) {
        return ok(religionService.fetchReligion(id));
    }

}
