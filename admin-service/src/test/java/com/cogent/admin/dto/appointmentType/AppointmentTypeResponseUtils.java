package com.cogent.admin.dto.appointmentType;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import com.cogent.persistence.model.AppointmentType;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-09-26
 */
public class AppointmentTypeResponseUtils {
    public static AppointmentType getAppointmentType() {
        return new AppointmentType(1L, "DOCTOR", 'Y', null);
    }

    public static AppointmentType getUpdatedAppointmentType() {
        return new AppointmentType(1L, "DOCTOR", 'N', "yes. Inactive it!");
    }

    public static AppointmentType getDeletedAppointmentType() {
        return new AppointmentType(1L, "DOCTOR", 'D', "Yes, Delete it!!");
    }

    public static List<AppointmentTypeMinimalResponseDTO> fetchAppointmentTypeMinimalResponseDTO() {
        return Arrays.asList(AppointmentTypeMinimalResponseDTO.builder()
                        .id(1L)
                        .name("DOCTOR")
                        .status('Y')
                        .build(),
                AppointmentTypeMinimalResponseDTO.builder()
                        .id(2L)
                        .name("PACKAGE")
                        .status('Y')
                        .build()
        );
    }

    public static AppointmentTypeResponseDTO fetchAppointmentTypeResponseDTO() {
        return AppointmentTypeResponseDTO.builder()
                .remarks("yes. Inactive it!")
                .build();
    }

    public static List<DropDownResponseDTO> fetchAppointmentTypeForDropDown() {
        return Arrays.asList(DropDownResponseDTO.builder()
                        .value(1L)
                        .label("DOCTOR")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("PACKAGE")
                        .build()
        );
    }
}
