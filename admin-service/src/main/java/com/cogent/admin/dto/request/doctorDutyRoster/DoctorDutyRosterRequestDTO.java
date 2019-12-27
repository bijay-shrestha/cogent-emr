package com.cogent.admin.dto.request.doctorDutyRoster;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author smriti on 26/11/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDutyRosterRequestDTO implements Serializable {

    @NotNull
    private Long doctorId;

    @NotNull
    private Long specializationId;

    @NotNull
    private Long doctorTypeId;

    @NotNull
    private Integer rosterGapDuration;

    @NotNull
    private Date fromDate;

    @NotNull
    private Date toDate;

    @NotNull
    @Status
    private Character status;

    @NotNull
    @Status
    private Character hasOverrideDutyRoster;

    @NotEmpty
    private List<DoctorWeekDaysDutyRosterRequestDTO> doctorWeekDaysDutyRosterRequestDTOS;

    private List<DoctorDutyRosterOverrideRequestDTO> doctorDutyRosterOverrideRequestDTOS;
}
