package com.cogent.admin.feign.service;

import com.cogent.admin.feign.dto.request.appointment.AppointmentCountRequestDTO;
import com.cogent.admin.feign.dto.response.appointment.AppointmentBookedDateResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.cogent.admin.constants.MicroServiceConstants.API_V1;
import static com.cogent.admin.constants.MicroServiceConstants.AppointmentMicroService.BASE_NAME;
import static com.cogent.admin.constants.MicroServiceConstants.AppointmentMicroService.FETCH_APPOINTMENT_DATES;

/**
 * @author smriti ON 19/12/2019
 */
@FeignClient(BASE_NAME)
@RequestMapping(API_V1)
public interface AppointmentService {

    @PutMapping(FETCH_APPOINTMENT_DATES)
    List<AppointmentBookedDateResponseDTO> fetchAppointmentDates(@RequestBody AppointmentCountRequestDTO requestDTO);
}
