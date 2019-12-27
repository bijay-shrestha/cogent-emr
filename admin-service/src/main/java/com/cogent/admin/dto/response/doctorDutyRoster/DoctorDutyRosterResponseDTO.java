package com.cogent.admin.dto.response.doctorDutyRoster;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti on 29/11/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDutyRosterResponseDTO implements Serializable {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private Long specializationId;

    private String specializationName;

    private Long doctorTypeId;

    private String doctorTypeName;

    private Integer rosterGapDuration;

    private Date fromDate;

    private Date toDate;

    private Character status;

    private String remarks;

    private Character hasOverrideDutyRoster;
}
