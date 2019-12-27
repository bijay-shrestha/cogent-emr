package com.cogent.admin.dto.specialization;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.response.specialization.SpecializationMinimalResponseDTO;
import com.cogent.admin.dto.response.specialization.SpecializationResponseDTO;
import com.cogent.persistence.model.Specialization;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-09-25
 */
public class SpecializationResponseUtils {
    public static Specialization getSpecialization() {
        return new Specialization(1L, "PHYSICIAN", 'Y', null);
    }

    public static Specialization getUpdatedSpecialization() {
        return new Specialization(1L, "PHYSICIAN", 'N', "yes. Inactive it!");
    }

    public static Specialization getDeletedSpecialization() {
        return new Specialization(1L, "PHYSICIAN", 'D', "Yes, Delete it!!");
    }

    public static List<SpecializationMinimalResponseDTO> fetchSpecializationMinimalResponseDTO() {
        return Arrays.asList(SpecializationMinimalResponseDTO.builder()
                        .id(1L)
                        .name("PHYSICIAN")
                        .status('Y')
                        .build(),
                SpecializationMinimalResponseDTO.builder()
                        .id(2L)
                        .name("SURGEON")
                        .status('Y')
                        .build()
        );
    }

    public static SpecializationResponseDTO fetchSpecializationResponseDTO() {
        return SpecializationResponseDTO.builder()
                .remarks("yes. Inactive it!")
                .build();
    }

    public static List<DropDownResponseDTO> fetchSpecializationForDropDown() {
        return Arrays.asList(DropDownResponseDTO.builder()
                        .value(1L)
                        .label("PHYSICIAN")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("SURGEON")
                        .build()
        );
    }
}
