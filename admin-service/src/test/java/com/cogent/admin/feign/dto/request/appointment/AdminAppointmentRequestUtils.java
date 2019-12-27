package com.cogent.admin.feign.dto.request.appointment;

/**
 * @author smriti on 2019-10-26
 */
public class AdminAppointmentRequestUtils {

    public static AdminAppointmentRequestDTO getAdminAppointmentRequestDTO() {
        return AdminAppointmentRequestDTO.builder()
                .appointmentModeId(1L)
                .patientTypeId(1L)
                .doctorId(1L)
                .appointmentTypeId(1L)
                .billTypeId(1L)
                .build();
    }
}
