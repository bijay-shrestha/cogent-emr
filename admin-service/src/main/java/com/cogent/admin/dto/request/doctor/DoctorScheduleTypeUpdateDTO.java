package com.cogent.admin.dto.request.doctor;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 13/11/2019
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorScheduleTypeUpdateDTO implements Serializable {

    private Long doctorScheduleTypeId;

    @NotNull
    private Long doctorTypeId;

    @NotNull
    @Status
    private Character status;
}
