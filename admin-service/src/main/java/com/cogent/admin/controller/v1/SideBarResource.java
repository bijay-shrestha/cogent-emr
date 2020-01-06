package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.request.profile.ProfileMenuSearchRequestDTO;
import com.cogent.admin.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.SideBarConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.SideBarConstant.FETCH_ASSIGNED_PROFILE_RESPONSE;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.SidebarConstants.BASE_SIDE_BAR;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti ON 27/12/2019
 */
@RestController
@RequestMapping(value = API_V1 + BASE_SIDE_BAR)
@Api(BASE_API_VALUE)
public class SideBarResource {

    private final ProfileService profileService;

    public SideBarResource(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping
    @ApiOperation(FETCH_ASSIGNED_PROFILE_RESPONSE)
    public ResponseEntity<?> fetchAssignedProfileResponse(@Valid @RequestBody ProfileMenuSearchRequestDTO requestDTO) {
        return ok(profileService.fetchAssignedProfileResponseDto(requestDTO));
    }

    @GetMapping("/test")
    public String hello(){
        return "hello";
    }
}
