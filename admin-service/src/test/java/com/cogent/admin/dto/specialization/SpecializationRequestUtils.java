package com.cogent.admin.dto.specialization;

import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;

/**
 * @author smriti on 2019-09-25
 */
public class SpecializationRequestUtils {
    public static SpecializationRequestDTO getInvalidInputForSave() {
        return SpecializationRequestDTO.builder()
                .name("")
                .status('M')
                .build();
    }

    public static SpecializationRequestDTO getSpecializationRequestDTO() {
        return SpecializationRequestDTO.builder()
                .name("Physician")
                .status('Y')
                .build();
    }

    public static SpecializationUpdateRequestDTO getInvalidInputForUpdate() {
        return SpecializationUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    public static SpecializationUpdateRequestDTO getSpecializationUpdateRequestDTO() {
        return SpecializationUpdateRequestDTO.builder()
                .id(1L)
                .name("Physician")
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static SpecializationSearchRequestDTO getSpecializationSearchRequestDTO() {
        return SpecializationSearchRequestDTO.builder()
                .name("Physician")
                .status('Y')
                .build();
    }
}
