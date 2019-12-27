package com.cogent.admin.dto.request.doctor;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-10-18
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDepartmentUpdateDTO implements Serializable {

    private Long doctorDepartmentId;

    @NotNull
    private Long departmentId;

    @NotNull
    @Status
    private Character status;
}
