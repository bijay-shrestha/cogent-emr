package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;
import com.cogent.admin.service.SurnameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.SurnameConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.SurnameConstants.BASE_SURNAME;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.*;

/**
 * @author smriti on 2019-09-12
 */
@RestController
@RequestMapping(API_V1 + BASE_SURNAME)
@Api(BASE_API_VALUE)
public class SurnameController {

    private final SurnameService surnameService;

    public SurnameController(SurnameService surnameService) {
        this.surnameService = surnameService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody SurnameRequestDTO requestDTO) {
        surnameService.save(requestDTO);
        return created(create(API_V1 + BASE_SURNAME + SAVE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody SurnameUpdateRequestDTO updateRequestDTO) {
        surnameService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        surnameService.delete(deleteRequestDTO);
        return noContent().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody SurnameSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(surnameService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchSurnameForDropDown() {
        return ok(surnameService.fetchActiveSurnameForDropDown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAIL_OPERATION)
    public ResponseEntity<?> fetchSurnameDetails(@PathVariable Long id) {
        return ok(surnameService.fetchSurnameDetails(id));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_SURNAME_OPERATION)
    public ResponseEntity<?> fetchSurname(@PathVariable Long id) {
        return ok(surnameService.fetchSurname(id));
    }
}
