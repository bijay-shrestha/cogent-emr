package com.cogent.admin.dto.appointmentMode;

import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;

/**
 * @author smriti on 2019-10-10
 */
public class AppointmentModeRequestUtils {
    public static AppointmentModeRequestDTO getInvalidInputForSave() {
        return AppointmentModeRequestDTO.builder()
                .name("")
                .status('M')
                .build();
    }

    public static AppointmentModeRequestDTO getAppointmentModeRequestDTO() {
        return AppointmentModeRequestDTO.builder()
                .name("Online")
                .status('Y')
                .build();
    }

    public static AppointmentModeUpdateRequestDTO getInvalidInputForUpdate() {
        return AppointmentModeUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    public static AppointmentModeUpdateRequestDTO getAppointmentModeUpdateRequestDTO() {
        return AppointmentModeUpdateRequestDTO.builder()
                .id(1L)
                .name("Online")
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static AppointmentModeSearchRequestDTO getAppointmentModeSearchRequestDTO() {
        return AppointmentModeSearchRequestDTO.builder()
                .name("Online")
                .status('Y')
                .build();
    }

}
