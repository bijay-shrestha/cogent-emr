package com.cogent.admin.dto.request.patientType;

/**
 * @author smriti on 2019-09-26
 */
public class PatientTypeRequestUtils {
    public static PatientTypeRequestDTO getInvalidInputForSave() {
        return PatientTypeRequestDTO.builder()
                .name("")
                .status('M')
                .build();
    }

    public static PatientTypeRequestDTO getPatientTypeRequestDTO() {
        return PatientTypeRequestDTO.builder()
                .name("Inpatient")
                .status('Y')
                .build();
    }

    public static PatientTypeUpdateRequestDTO getInvalidInputForUpdate() {
        return PatientTypeUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    public static PatientTypeUpdateRequestDTO getPatientTypeUpdateRequestDTO() {
        return PatientTypeUpdateRequestDTO.builder()
                .id(1L)
                .name("Inpatient")
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static PatientTypeSearchRequestDTO getPatientTypeSearchRequestDTO() {
        return PatientTypeSearchRequestDTO.builder()
                .name("Inpatient")
                .status('Y')
                .build();
    }
}
