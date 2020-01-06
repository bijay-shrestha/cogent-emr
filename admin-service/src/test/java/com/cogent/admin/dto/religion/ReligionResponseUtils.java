package com.cogent.admin.dto.religion;

import com.cogent.admin.dto.response.religion.ReligionDropdownDTO;
import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import com.cogent.persistence.model.Religion;

import java.util.Arrays;
import java.util.List;

public class ReligionResponseUtils {

    public static Religion getReligion() {
        return new Religion(1L, "HINDUISM", 'Y', null);
    }

    public static Religion getDeletedReligion() {
        return new Religion(1L, "HINDUISM", 'D', "Yes, Delete it!!");
    }

    public static List<ReligionResponseDTO> fetchReligionResponseDTO() {
        return Arrays.asList(ReligionResponseDTO.builder()
                        .id(1L)
                        .name("CHRISTIAN")
                        .status('Y')
                        .build(),
                ReligionResponseDTO.builder()
                        .id(2L)
                        .name("BUDDHISM")
                        .status('Y')
                        .build()
        );
    }

    public static List<ReligionDropdownDTO> fetchReligionForDropDown() {

        return Arrays.asList(ReligionDropdownDTO.builder()
                        .value(1L)
                        .label("CHRISTIAN")
                        .build(),
                ReligionDropdownDTO.builder()
                        .value(2L)
                        .label("BUDDHISM")
                        .build()
        );
    }
}
