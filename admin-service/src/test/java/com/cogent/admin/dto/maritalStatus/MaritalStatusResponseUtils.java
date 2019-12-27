package com.cogent.admin.dto.maritalStatus;

import com.cogent.admin.dto.response.maritalStatus.MaritalStatusDropdownDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import com.cogent.persistence.model.MaritalStatus;

import java.util.Arrays;
import java.util.List;

public class MaritalStatusResponseUtils {


    public static MaritalStatus getMaritalStatus() {
        return new MaritalStatus(1L, "Married", 'Y', null);
    }

    public static MaritalStatus getDeletedMaritalStatus() {
        return new MaritalStatus(1L, "MARRIED", 'D', "Yes, Delete it!!");
    }

    public static List<MaritalStatusResponseDTO> fetchMaritalStatusResponseDTO() {
        return Arrays.asList(MaritalStatusResponseDTO.builder()
                        .id(1L)
                        .name("MARRIED")
                        .status('Y')
                        .build(),
                MaritalStatusResponseDTO.builder()
                        .id(2L)
                        .name("UNMARRIED")
                        .status('Y')
                        .build()
        );
    }

    public static List<MaritalStatusDropdownDTO> fetchMaritalStatusForDropDown() {

        return Arrays.asList(MaritalStatusDropdownDTO.builder()
                        .value(1L)
                        .label("MARRIED")
                        .build(),
                MaritalStatusDropdownDTO.builder()
                        .value(2L)
                        .label("UNMARRIED")
                        .build()
        );
    }

}
