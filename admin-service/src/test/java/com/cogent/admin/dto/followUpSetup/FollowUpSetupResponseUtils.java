package com.cogent.admin.dto.followUpSetup;

import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupMinimalResponseDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupResponseDTO;
import com.cogent.persistence.model.FollowUpSetup;

import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.request.patientType.PatientTypeResponseUtils.getPatientType;

/**
 * @author smriti on 2019-11-04
 */
public class FollowUpSetupResponseUtils {
    public static FollowUpSetup getFollowUpSetup() {
        return new FollowUpSetup(1L, 16, 1,
                'Y', getPatientType(), 'Y', null);
    }

    public static FollowUpSetup getUpdatedFollowUpSetup() {
        return new FollowUpSetup(1L, 15, 1,
                'Y', getPatientType(), 'Y', "update interval");
    }

    public static List<FollowUpSetupMinimalResponseDTO> getFollowUpSetupMinimalResponseDTOS() {
        return Arrays.asList(FollowUpSetupMinimalResponseDTO.builder()
                        .id(1L)
                        .followUpIntervals(15)
                        .numberOfFollowUps(1)
                        .followUpApplicable('Y')
                        .patientTypeName("INPATIENT")
                        .status('Y')
                        .build(),
                FollowUpSetupMinimalResponseDTO.builder()
                        .id(2L)
                        .followUpIntervals(10)
                        .numberOfFollowUps(2)
                        .followUpApplicable('Y')
                        .patientTypeName("OUTPATIENT")
                        .status('Y')
                        .build()
        );
    }

    public static FollowUpSetupResponseDTO getFollowUpSetupResponseDTO() {
        return FollowUpSetupResponseDTO.builder()
                .followUpIntervals(15)
                .numberOfFollowUps(1)
                .followUpApplicable('Y')
                .patientTypeId(1L)
                .patientTypeName("INPATIENT")
                .status('Y')
                .remarks(null)
                .build();
    }

}
