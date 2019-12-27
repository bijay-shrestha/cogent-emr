package com.cogent.admin.dto.response.doctorDutyRoster;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti ON 10/12/2019
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDutyRosterTimeResponseDTO implements Serializable {

    private Date startTime;

    private Date endTime;

    private Character dayOffStatus;
}
