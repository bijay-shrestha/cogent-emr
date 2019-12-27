package com.cogent.admin.dto.request.followUpSetup;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-11-04
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowUpSetupUpdateRequestDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private Integer followUpIntervals;

    @NotNull
    private Integer numberOfFollowUps;

    @NotNull
    @Status
    private Character followUpApplicable;

    @NotNull
    private Long patientTypeId;

    @NotNull
    @Status
    private Character status;

    @NotNull
    @NotEmpty
    private String remarks;
}
