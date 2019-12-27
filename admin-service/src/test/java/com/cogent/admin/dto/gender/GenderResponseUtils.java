package com.cogent.admin.dto.gender;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.Gender;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 08/11/2019
 */
public class GenderResponseUtils {
    public static List<DropDownResponseDTO> fetchActiveGenders() {
        return Arrays.asList(
                DropDownResponseDTO.builder()
                        .value(1L)
                        .label("MALE")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("FEMALE")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(3L)
                        .label("OTHERS")
                        .build()
        );
    }

    public static Gender fetchGender() {
        return new Gender(1L, "FEMALE", 'Y');
    }

}
