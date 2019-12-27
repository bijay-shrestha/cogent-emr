package com.cogent.admin.controller.v1;

import com.cogent.admin.service.GenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.GenderConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.GenderConstant.FETCH_ACTIVE_GENDER;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.GenderConstants.BASE_GENDER;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 08/11/2019
 */
@RestController
@RequestMapping(API_V1 + BASE_GENDER)
@Api(BASE_API_VALUE)
public class GenderController {

    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping
    @ApiOperation(FETCH_ACTIVE_GENDER)
    public ResponseEntity<?> fetchActiveGender() {
        return ok(genderService.fetchActiveGender());
    }
}
