package com.cogent.admin.dto.response.followUpSetup;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-11-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FollowUpSetupResponseDTO implements Serializable {

    private Integer followUpIntervals;

    private Integer numberOfFollowUps;

    private Character followUpApplicable;

    private Long patientTypeId;

    private String patientTypeName;

    private Character status;

    private String remarks;
}
