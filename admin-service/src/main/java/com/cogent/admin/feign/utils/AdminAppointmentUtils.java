package com.cogent.admin.feign.utils;

import com.cogent.admin.feign.dto.response.appointment.AdminAppointmentResponseDTO;
import com.cogent.persistence.model.*;

/**
 * @author smriti on 2019-10-23
 */
public class AdminAppointmentUtils {

    public static AdminAppointmentResponseDTO parseToAppointmentResponseDTO(AppointmentType appointmentType,
                                                                            AppointmentMode appointmentMode,
                                                                            PatientType patientType,
                                                                            Doctor doctor,
                                                                            Specialization specialization,
                                                                            BillType billType) {
        return AdminAppointmentResponseDTO.builder()
                .appointmentType(appointmentType)
                .appointmentMode(appointmentMode)
                .patientType(patientType)
                .doctor(doctor)
                .specialization(specialization)
                .billType(billType)
                .build();
    }
}
