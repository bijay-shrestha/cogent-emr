package com.cogent.admin.dto.request.patientType;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import com.cogent.persistence.model.PatientType;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-09-26
 */
public class PatientTypeResponseUtils {
    public static PatientType getPatientType() {
        return new PatientType(1L, "INPATIENT", "IP", 'Y', null);
    }

    public static PatientType getUpdatedPatientType() {
        return new PatientType(1L, "INPATIENT", "IP", 'N', "yes. Inactive it!");
    }

    public static PatientType getDeletedPatientType() {
        return new PatientType(1L, "INPATIENT", "IP",'D', "Yes, Delete it!!");
    }

    public static List<PatientTypeMinimalResponseDTO> fetchPatientTypeMinimalResponseDTO() {
        return Arrays.asList(PatientTypeMinimalResponseDTO.builder()
                        .id(1L)
                        .name("INPATIENT")
                        .status('Y')
                        .build(),
                PatientTypeMinimalResponseDTO.builder()
                        .id(2L)
                        .name("OUTPATIENT")
                        .status('Y')
                        .build()
        );
    }

    public static PatientTypeResponseDTO fetchPatientTypeResponseDTO() {
        return PatientTypeResponseDTO.builder()
                .remarks("yes. Inactive it!")
                .build();
    }

    public static List<DropDownResponseDTO> fetchPatientTypeForDropDown() {
        return Arrays.asList(DropDownResponseDTO.builder()
                        .value(1L)
                        .label("INPATIENT")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("OUTPATIENT")
                        .build()
        );
    }
}
