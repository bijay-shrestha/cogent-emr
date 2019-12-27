package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityUpdateRequestDTO;
import com.cogent.admin.service.NationalityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.NationalityConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.NationalityConstants.BASE_NATIONALITY;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping(API_V1 + BASE_NATIONALITY)
@Api(value = BASE_API_VALUE)
public class NationalityController {

    private final NationalityService nationalityService;

    public NationalityController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    @PostMapping
    @ApiOperation(SAVE_NATIONALITY_OPERATION)
    public ResponseEntity<?> saveNationality(@Valid @RequestBody NationalityRequestDTO requestDTO) {
        nationalityService.createNationality(requestDTO);
        return created(URI.create(API_V1 + BASE_NATIONALITY)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_NATIONALITY_OPERATION)
    public ResponseEntity<?> deleteNationality(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        nationalityService.deleteNationality(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_NATIONALITY_OPERATION)
    public ResponseEntity<?> updateNationality(@Valid @RequestBody NationalityUpdateRequestDTO updateRequestDTO) {
        nationalityService.updateNationality(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_NATIONALITY_OPERATION)
    public ResponseEntity<?> searchNatioanlity(@RequestBody NationalitySearchRequestDTO searchRequestDTO,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(nationalityService.searchNationality(searchRequestDTO, pageable));
    }

    @GetMapping(ID_PATH_VARIABLE_BASE)
    @ApiOperation(NATIONALITY_OPERATION)
    public ResponseEntity<?> fetchNationality(@PathVariable Long id) {
        return ok(nationalityService.fetchNatioanlity(id));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(NATIONALITY_DETAIL_OPERATION)
    public ResponseEntity<?> fetchNationalityDetails(@PathVariable Long id) {
        return ok(nationalityService.fetchNatioanlityDetails(id));
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_DETAILS_FOR_NATIONALITY_DROPDOWN)
    public ResponseEntity<?> dropDown() {
        return ok(nationalityService.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_NATIONALITY_DROPDOWN)
    public ResponseEntity<?> activeDropDown() {
        return ok(nationalityService.fetchActiveDropDownList());
    }
}
