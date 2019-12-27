package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;
import com.cogent.admin.service.FollowUpSetupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.FollowUpSetupConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.FollowUpSetupConstants.BASE_FOLLOW_UP_SETUP;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-11-04
 */
@RestController
@RequestMapping(API_V1 + BASE_FOLLOW_UP_SETUP)
@Api(BASE_API_VALUE)
public class FollowUpSetupController {

    private final FollowUpSetupService followUpSetupService;

    public FollowUpSetupController(FollowUpSetupService followUpSetupService) {
        this.followUpSetupService = followUpSetupService;
    }

    @PostMapping
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@Valid @RequestBody FollowUpSetupRequestDTO requestDTO) {
        followUpSetupService.save(requestDTO);
        return created(create(API_V1 + BASE_FOLLOW_UP_SETUP)).build();
    }

    @PutMapping
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@Valid @RequestBody FollowUpSetupUpdateRequestDTO updateRequestDTO) {
        followUpSetupService.update(updateRequestDTO);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        followUpSetupService.delete(deleteRequestDTO);
        return ok().build();
    }

    @GetMapping
    @ApiOperation(FETCH_MINIMAL_OPERATION)
    public ResponseEntity<?> search() {
        return ok().body(followUpSetupService.fetchFollowUpSetup());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(followUpSetupService.fetchDetailsById(id));
    }
}
