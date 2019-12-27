package com.cogent.admin.dto.appointmentType;

import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;

/**
 * @author smriti on 2019-09-26
 */
public class AppointmentTypeRequestUtils {
    public static AppointmentTypeRequestDTO getInvalidInputForSave() {
        return AppointmentTypeRequestDTO.builder()
                .name("")
                .status('M')
                .build();
    }

    public static AppointmentTypeRequestDTO getAppointmentTypeRequestDTO() {
        return AppointmentTypeRequestDTO.builder()
                .name("Doctor")
                .status('Y')
                .build();
    }

    public static AppointmentTypeUpdateRequestDTO getInvalidInputForUpdate() {
        return AppointmentTypeUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    public static AppointmentTypeUpdateRequestDTO getAppointmentTypeUpdateRequestDTO() {
        return AppointmentTypeUpdateRequestDTO.builder()
                .id(1L)
                .name("Doctor")
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static AppointmentTypeSearchRequestDTO getAppointmentTypeSearchRequestDTO() {
        return AppointmentTypeSearchRequestDTO.builder()
                .name("Doctor")
                .status('Y')
                .build();
    }
}
