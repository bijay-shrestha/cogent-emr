package com.cogent.admin.feign.service;

import com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestDTO;
import com.cogent.admin.feign.dto.response.appointment.AdminAppointmentResponseDTO;

/**
 * @author smriti on 2019-10-23
 */
public interface AdminAppointmentService {

    AdminAppointmentResponseDTO fetchAdminDetails(AdminAppointmentRequestDTO requestDTO);
}
