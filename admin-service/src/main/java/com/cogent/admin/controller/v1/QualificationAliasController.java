package com.cogent.admin.controller.v1;

import com.cogent.admin.service.QualificationAliasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.QualificationAliasConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.QualificationAliasConstant.FETCH_ACTIVE_QUALIFICATION_ALIAS;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.QualificationAliasConstants.BASE_QUALIFICATION_ALIAS;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 11/11/2019
 */
@RestController
@RequestMapping(API_V1 + BASE_QUALIFICATION_ALIAS)
@Api(BASE_API_VALUE)
public class QualificationAliasController {

    private final QualificationAliasService qualificationAliasService;

    public QualificationAliasController(QualificationAliasService qualificationAliasService) {
        this.qualificationAliasService = qualificationAliasService;
    }

    @GetMapping
    @ApiOperation(FETCH_ACTIVE_QUALIFICATION_ALIAS)
    public ResponseEntity<?> fetchActiveQualificationAlias() {
        return ok(qualificationAliasService.fetchActiveQualificationAlias());
    }
}
