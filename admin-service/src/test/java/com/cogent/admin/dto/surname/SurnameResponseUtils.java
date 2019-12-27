package com.cogent.admin.dto.surname;

import com.cogent.admin.dto.response.surname.SurnameDropdownDTO;
import com.cogent.admin.dto.response.surname.SurnameMinimalResponseDTO;
import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.persistence.model.Surname;

import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.ethnicity.EthnicityTestUtils.getEthnicityInfo;

/**
 * @author smriti on 2019-09-12
 */
public class SurnameResponseUtils {

    public static Surname getSurname() {
        return new Surname(1L, "NEPAL", 'Y', null, getEthnicityInfo());
    }

    public static Surname getUpdatedSurname() {
        return new Surname(1L, "NEPALI", 'N', "yes. Inactive it!", getEthnicityInfo());
    }

    public static Surname getDeletedSurname() {
        return new Surname(1L, "NEPAL", 'D', "Yes, Delete it!!", getEthnicityInfo());
    }

    public static List<SurnameMinimalResponseDTO> fetchSurnameMinimalResponseDTO() {
        return Arrays.asList(SurnameMinimalResponseDTO.builder()
                        .id(1L)
                        .name("NEPAL")
                        .status('Y')
                        .ethnicityName("CHHETRI")
                        .build(),
                SurnameMinimalResponseDTO.builder()
                        .id(2L)
                        .name("NEPALI")
                        .status('Y')
                        .ethnicityName("NEWAR")
                        .build()
        );
    }

    public static List<SurnameDropdownDTO> fetchSurnameForDropDown() {
        return Arrays.asList(SurnameDropdownDTO.builder()
                        .value(1L)
                        .label("NEPAL")
                        .build(),
                SurnameDropdownDTO.builder()
                        .value(2L)
                        .label("NEPALI")
                        .build()
        );
    }

    public static SurnameResponseDTO fetchSurnameResponseDTO() {
        return SurnameResponseDTO.builder()
                .name("NEPAL")
                .status('Y')
                .remarks("update name")
                .ethnicityName("CHHETRI")
                .build();
    }
}
