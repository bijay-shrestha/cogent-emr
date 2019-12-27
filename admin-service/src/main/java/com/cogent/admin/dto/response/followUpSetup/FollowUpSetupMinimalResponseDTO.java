package com.cogent.admin.dto.response.followUpSetup;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-11-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FollowUpSetupMinimalResponseDTO implements Serializable {

    private Long id;

    private Integer followUpIntervals;

    private Integer numberOfFollowUps;

    private Character followUpApplicable;

    private String patientTypeName;

    private Character status;
}
