package com.cogent.admin.feign.controller;

import com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestDTO;
import com.cogent.admin.feign.dto.response.appointment.AdminAppointmentResponseDTO;
import com.cogent.admin.feign.service.AdminAppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import static com.cogent.admin.constants.MicroServiceConstants.AppointmentMicroService.FETCH_ADMIN_DETAILS;
import static com.cogent.admin.constants.SwaggerConstants.AppointmentConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.AppointmentConstant.DETAILS_OPERATION;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;

/**
 * @author smriti on 2019-10-23
 */
@RestController
@RequestMapping(API_V1)
@Api(BASE_API_VALUE)
public class AppointmentController {

    private final AdminAppointmentService adminAppointmentService;

    public AppointmentController(AdminAppointmentService adminAppointmentService) {
        this.adminAppointmentService = adminAppointmentService;
    }

    @PutMapping(FETCH_ADMIN_DETAILS)
    @ApiOperation(DETAILS_OPERATION)
    public AdminAppointmentResponseDTO fetchAdminDetails(@RequestBody AdminAppointmentRequestDTO requestDTO) {
        return adminAppointmentService.fetchAdminDetails(requestDTO);
    }
}
