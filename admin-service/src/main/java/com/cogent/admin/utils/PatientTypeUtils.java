package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeUpdateRequestDTO;
import com.cogent.persistence.model.PatientType;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-09-26
 */
public class PatientTypeUtils {

    public static PatientType convertDTOToPatientType(PatientTypeRequestDTO requestDTO) {
        PatientType patientType = new PatientType();
        patientType.setName(toUpperCase(requestDTO.getName()));
        patientType.setStatus(requestDTO.getStatus());
        return patientType;
    }

    public static PatientType convertToUpdatedPatientType(PatientTypeUpdateRequestDTO updateRequestDTO,
                                                                  PatientType patientType) {

        patientType.setName(toUpperCase(updateRequestDTO.getName()));
        patientType.setStatus(updateRequestDTO.getStatus());
        patientType.setRemarks(updateRequestDTO.getRemarks());
        return patientType;
    }

    public static PatientType convertToDeletedPatientType(PatientType patientType,
                                                              DeleteRequestDTO deleteRequestDTO) {

        patientType.setStatus(deleteRequestDTO.getStatus());
        patientType.setRemarks(deleteRequestDTO.getRemarks());
        return patientType;
    }
}
