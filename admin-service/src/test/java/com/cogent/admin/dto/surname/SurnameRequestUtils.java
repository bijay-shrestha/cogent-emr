package com.cogent.admin.dto.surname;

import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;

/**
 * @author smriti on 2019-09-12
 */
public class SurnameRequestUtils {

    public static SurnameRequestDTO getInvalidInputForSave() {
        return SurnameRequestDTO.builder()
                .name("")
                .status('M')
                .ethnicityId(1L)
                .build();
    }

    public static SurnameRequestDTO getSurnameRequestDTO() {
        return SurnameRequestDTO.builder()
                .name("Nepal")
                .status('Y')
                .ethnicityId(1L)
                .build();
    }

    public static SurnameUpdateRequestDTO getInvalidInputForUpdate() {
        return SurnameUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .ethnicityId(1L)
                .build();
    }

    public static SurnameUpdateRequestDTO getSurnameUpdateRequestDTO() {
        return SurnameUpdateRequestDTO.builder()
                .id(1L)
                .name("Nepali")
                .status('N')
                .remarks("yes. Inactive it!")
                .ethnicityId(1L)
                .build();
    }

    public static SurnameSearchRequestDTO getSurnameSearchRequestDTO() {
        return SurnameSearchRequestDTO.builder()
                .name("Nepal")
                .status('Y')
                .ethnicityId(1L)
                .build();
    }
}
