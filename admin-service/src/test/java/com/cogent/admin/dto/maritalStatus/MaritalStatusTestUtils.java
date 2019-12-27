package com.cogent.admin.dto.maritalStatus;

import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;

public class MaritalStatusTestUtils {

    public static MaritalStatusRequestDTO getInvalidInputForSave() {
        return MaritalStatusRequestDTO.builder()
                .name("")
                .status('X')
                .build();
    }

    public static MaritalStatusRequestDTO getMaritalStatusRequestDTO() {
        return MaritalStatusRequestDTO.builder()
                .name("Married")
                .status('Y')
                .build();
    }

    public static MaritalStatusUpdateRequestDTO getInvalidInputForUpdate() {
        return MaritalStatusUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('X')
                .build();
    }

    public static MaritalStatusUpdateRequestDTO getMaritalStatusUpdateRequestDTO() {
        return MaritalStatusUpdateRequestDTO.builder()
                .id(1L)
                .name("Married")
                .status('N')
                .remarks("OK. Inactive it!")
                .build();
    }

    public static MaritalStatusSearchRequestDTO getMaritalStatusSearchRequestDTO() {

        return MaritalStatusSearchRequestDTO.builder()
                .name("Married")
                .status('Y')
                .build();
    }


}
