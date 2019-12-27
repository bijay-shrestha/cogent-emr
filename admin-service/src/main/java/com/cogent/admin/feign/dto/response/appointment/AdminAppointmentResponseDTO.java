package com.cogent.admin.feign.dto.response.appointment;

import com.cogent.persistence.model.*;
import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-10-23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAppointmentResponseDTO implements Serializable {

    private PatientType patientType;

    private AppointmentType appointmentType;

    private AppointmentMode appointmentMode;

    private Doctor doctor;

    private Specialization specialization;

    private BillType billType;
}
