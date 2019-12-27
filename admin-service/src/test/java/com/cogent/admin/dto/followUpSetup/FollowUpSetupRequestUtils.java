package com.cogent.admin.dto.followUpSetup;

import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;

/**
 * @author smriti on 2019-11-04
 */
public class FollowUpSetupRequestUtils {

    public static FollowUpSetupRequestDTO getInvalidInputForSave() {
        return FollowUpSetupRequestDTO.builder()
                .status('M')
                .build();
    }

    public static FollowUpSetupRequestDTO getFollowUpSetupRequestDTO() {
        return FollowUpSetupRequestDTO.builder()
                .followUpIntervals(16)
                .numberOfFollowUps(1)
                .patientTypeId(1L)
                .followUpApplicable('Y')
                .status('Y')
                .build();
    }

    public static FollowUpSetupUpdateRequestDTO getInvalidInputForUpdate() {
        return FollowUpSetupUpdateRequestDTO.builder()
                .id(null)
                .status('M')
                .build();
    }

    public static FollowUpSetupUpdateRequestDTO getFollowUpSetupUpdateRequestDTO() {
        return FollowUpSetupUpdateRequestDTO.builder()
                .id(1L)
                .followUpIntervals(15)
                .numberOfFollowUps(1)
                .patientTypeId(1L)
                .followUpApplicable('Y')
                .status('Y')
                .remarks("update interval")
                .build();
    }

}
