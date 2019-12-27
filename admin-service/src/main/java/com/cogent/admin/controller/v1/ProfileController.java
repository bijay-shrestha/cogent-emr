package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateRequestDTO;
import com.cogent.admin.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.ProfileConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ProfileSetupConstants.BASE_PROFILE;
import static com.cogent.admin.constants.WebResourceKeyConstants.SubDepartmentConstants.SUB_DEPARTMENT_ID_PATH_VARIABLE_BASE;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 7/2/19
 */
@RestController
@RequestMapping(value = API_V1 + BASE_PROFILE)
@Api(BASE_API_VALUE)
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody ProfileRequestDTO requestDTO) {
        profileService.save(requestDTO);
        return created(create(API_V1 + BASE_PROFILE)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<Void> update(@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
        profileService.update(requestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> deleteProfile(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        profileService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody ProfileSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(profileService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(profileService.fetchDetailsById(id));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchProfilesForDropdown() {
        return ok(profileService.fetchActiveProfilesForDropdown());
    }

    @GetMapping(SUB_DEPARTMENT_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_PROFILE_BY_SUB_DEPARTMENT_ID)
    public ResponseEntity<?> fetchProfilesForDropdown(@PathVariable("subDepartmentId") Long subDepartmentId) {
        return ok(profileService.fetchProfileBySubDepartmentId(subDepartmentId));
    }
}