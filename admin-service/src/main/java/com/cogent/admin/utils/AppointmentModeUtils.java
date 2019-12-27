package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;
import com.cogent.persistence.model.AppointmentMode;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-10-10
 */
public class AppointmentModeUtils {

    public static AppointmentMode convertDTOToAppointmentMode(AppointmentModeRequestDTO requestDTO) {
        AppointmentMode appointmentMode = new AppointmentMode();
        appointmentMode.setName(toUpperCase(requestDTO.getName()));
        appointmentMode.setStatus(requestDTO.getStatus());
        return appointmentMode;
    }

    public static AppointmentMode convertToUpdatedAppointmentMode(AppointmentModeUpdateRequestDTO updateRequestDTO,
                                                                  AppointmentMode appointmentMode) {

        appointmentMode.setName(toUpperCase(updateRequestDTO.getName()));
        appointmentMode.setStatus(updateRequestDTO.getStatus());
        appointmentMode.setRemarks(updateRequestDTO.getRemarks());
        return appointmentMode;
    }

    public static AppointmentMode convertToDeletedAppointmentMode(AppointmentMode appointmentMode,
                                                                  DeleteRequestDTO deleteRequestDTO) {
        appointmentMode.setStatus(deleteRequestDTO.getStatus());
        appointmentMode.setRemarks(deleteRequestDTO.getRemarks());
        return appointmentMode;
    }
}
