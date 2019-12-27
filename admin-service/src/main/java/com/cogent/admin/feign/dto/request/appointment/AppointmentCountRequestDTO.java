package com.cogent.admin.feign.dto.request.appointment;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti ON 18/12/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCountRequestDTO implements Serializable {

    private Date fromDate;

    private Date toDate;

    private Long doctorId;

    private Long specializationId;
}
