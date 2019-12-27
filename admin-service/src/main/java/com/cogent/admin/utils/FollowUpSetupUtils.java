package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;
import com.cogent.persistence.model.FollowUpSetup;
import com.cogent.persistence.model.PatientType;

/**
 * @author smriti on 2019-11-04
 */
public class FollowUpSetupUtils {

    public static FollowUpSetup convertDTOToFollowUpSetup(FollowUpSetupRequestDTO requestDTO,
                                                          PatientType patientType) {

        FollowUpSetup followUpSetup = new FollowUpSetup();
        followUpSetup.setFollowUpIntervals(requestDTO.getFollowUpIntervals());
        followUpSetup.setNumberOfFollowUps(requestDTO.getNumberOfFollowUps());
        followUpSetup.setFollowUpApplicable(requestDTO.getFollowUpApplicable());
        followUpSetup.setPatientType(patientType);
        followUpSetup.setStatus(requestDTO.getStatus());
        return followUpSetup;
    }

    public static void convertDTOToUpdatedFollowUpSetup(FollowUpSetupUpdateRequestDTO requestDTO,
                                                        PatientType patientType,
                                                        FollowUpSetup followUpSetup) {

        followUpSetup.setFollowUpIntervals(requestDTO.getFollowUpIntervals());
        followUpSetup.setNumberOfFollowUps(requestDTO.getNumberOfFollowUps());
        followUpSetup.setFollowUpApplicable(requestDTO.getFollowUpApplicable());
        followUpSetup.setPatientType(patientType);
        followUpSetup.setStatus(requestDTO.getStatus());
        followUpSetup.setRemarks(requestDTO.getRemarks());
    }

    public static void convertToDeletedFollowUpSetup(FollowUpSetup followUpSetup,
                                                     DeleteRequestDTO deleteRequestDTO) {
        followUpSetup.setRemarks(deleteRequestDTO.getRemarks());
        followUpSetup.setStatus(deleteRequestDTO.getStatus());
    }

}
