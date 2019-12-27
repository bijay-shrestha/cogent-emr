package com.cogent.admin.dto.request.appointmentMode;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti on 2019-10-10
 */
@Getter
@Builder
public class AppointmentModeRequestDTO implements Serializable {
    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String name;

    @NotNull
    @Status
    private Character status;
}
