package com.cogent.admin.controller.v1;

import com.cogent.admin.service.DoctorTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.DoctorTypeConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.DoctorTypeConstant.FETCH_ACTIVE_DOCTOR_TYPE;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorTypeConstants.BASE_DOCTOR_TYPE;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 10/11/2019
 */
@RestController
@RequestMapping(API_V1 + BASE_DOCTOR_TYPE)
@Api(BASE_API_VALUE)
public class DoctorTypeController {

    private final DoctorTypeService doctorTypeService;

    public DoctorTypeController(DoctorTypeService doctorTypeService) {
        this.doctorTypeService = doctorTypeService;
    }

    @GetMapping
    @ApiOperation(FETCH_ACTIVE_DOCTOR_TYPE)
    public ResponseEntity<?> fetchActiveDoctorType() {
        return ok(doctorTypeService.fetchActiveDoctorType());
    }
}
