package com.cogent.admin.controller.v1;

import com.cogent.admin.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.CountryConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.CountryConstant.FETCH_ACTIVE_COUNTRY;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.CountryConstants.BASE_COUNTRY;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 08/11/2019
 */
@RestController
@RequestMapping(API_V1 + BASE_COUNTRY)
@Api(BASE_API_VALUE)
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @ApiOperation(FETCH_ACTIVE_COUNTRY)
    public ResponseEntity<?> fetchActiveCountry() {
        return ok(countryService.fetchActiveCountry());
    }
}
