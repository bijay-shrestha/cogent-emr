package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;
import com.cogent.persistence.model.AppointmentType;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-09-26
 */
public class AppointmentTypeUtils {

    public static AppointmentType convertDTOToAppointmentType(AppointmentTypeRequestDTO requestDTO) {
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setName(toUpperCase(requestDTO.getName()));
        appointmentType.setStatus(requestDTO.getStatus());
        return appointmentType;
    }

    public static AppointmentType convertToUpdatedAppointmentType(AppointmentTypeUpdateRequestDTO updateRequestDTO,
                                                                  AppointmentType appointmentType) {

        appointmentType.setName(toUpperCase(updateRequestDTO.getName()));
        appointmentType.setStatus(updateRequestDTO.getStatus());
        appointmentType.setRemarks(updateRequestDTO.getRemarks());
        return appointmentType;
    }

    public static AppointmentType convertToDeletedAppointmentType(AppointmentType appointmentType,
                                                                  DeleteRequestDTO deleteRequestDTO) {

        appointmentType.setStatus(deleteRequestDTO.getStatus());
        appointmentType.setRemarks(deleteRequestDTO.getRemarks());
        return appointmentType;
    }
}
