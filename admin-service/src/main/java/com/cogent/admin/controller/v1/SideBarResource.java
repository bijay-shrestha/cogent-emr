package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.request.userMenu.UserMenuRequestDTO;
import com.cogent.admin.service.UserMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.SideBarConstant.FETCH_ASSIGNED_PROFILE_RESPONSE;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.SidebarConstants.BASE_SIDE_BAR;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti ON 27/12/2019
 */
@RestController
@RequestMapping(value = API_V1 + BASE_SIDE_BAR)
@Api
public class SideBarResource {

    private final UserMenuService userMenuService;

    public SideBarResource(UserMenuService userMenuService) {
        this.userMenuService = userMenuService;
    }

    @PostMapping
    @ApiOperation(FETCH_ASSIGNED_PROFILE_RESPONSE)
    public ResponseEntity<?> getUserMenuResponse(@Valid @RequestBody UserMenuRequestDTO requestDTO) {
        return ok(userMenuService.fetchAssignedProfileMenuResponseDto(requestDTO));
    }
}
