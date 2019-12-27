package com.cogent.admin.dto.appointmentMode;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import com.cogent.persistence.model.AppointmentMode;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-10-10
 */
public class AppointmentModeResponseUtils {
    public static AppointmentMode getAppointmentMode() {
        return new AppointmentMode(1L, "ONLINE", 'Y', null);
    }

    public static AppointmentMode getUpdatedAppointmentMode() {
        return new AppointmentMode(1L, "ONLINE", 'N', "yes. Inactive it!");
    }

    public static AppointmentMode getDeletedAppointmentMode() {
        return new AppointmentMode(1L, "ONLINE", 'D', "Yes, Delete it!!");
    }

    public static List<AppointmentModeMinimalResponseDTO> fetchAppointmentModeMinimalResponseDTO() {
        return Arrays.asList(AppointmentModeMinimalResponseDTO.builder()
                        .id(1L)
                        .name("ONLINE")
                        .status('Y')
                        .build(),
                AppointmentModeMinimalResponseDTO.builder()
                        .id(2L)
                        .name("WALK IN")
                        .status('Y')
                        .build()
        );
    }

    public static AppointmentModeResponseDTO fetchAppointmentModeResponseDTO() {
        return AppointmentModeResponseDTO.builder()
                .remarks("yes. Inactive it!")
                .build();
    }

    public static List<DropDownResponseDTO> fetchAppointmentModeForDropDown() {
        return Arrays.asList(DropDownResponseDTO.builder()
                        .value(1L)
                        .label("ONLINE")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("WALK IN")
                        .build()
        );
    }
}
