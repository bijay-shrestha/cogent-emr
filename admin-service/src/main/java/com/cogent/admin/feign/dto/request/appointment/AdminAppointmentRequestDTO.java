package com.cogent.admin.feign.dto.request.appointment;

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
public class AdminAppointmentRequestDTO implements Serializable {

    private Long patientTypeId;

    private Long appointmentTypeId;

    private Long appointmentModeId;

    private Long doctorId;

    private Long specializationId;

    private Long billTypeId;
}
