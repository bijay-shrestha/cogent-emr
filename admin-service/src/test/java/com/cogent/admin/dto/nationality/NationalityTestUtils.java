package com.cogent.admin.dto.nationality;

import com.cogent.admin.dto.request.nationality.NationalityRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityUpdateRequestDTO;
import com.cogent.admin.dto.response.nationality.NationalityDropDownResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityMinimalResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityResponseDTO;
import com.cogent.persistence.model.Nationality;

import java.util.Arrays;
import java.util.List;

public class NationalityTestUtils {
    public static NationalityRequestDTO getNationalityRequestDTO() {
        return NationalityRequestDTO.builder()
                .name("Nepalese")
                .status('Y')
                .build();
    }

    public static NationalitySearchRequestDTO getNationalitySeacrhRequestDTO() {
        return NationalitySearchRequestDTO.builder()
                .id(1L)
                .name("Nepali")
                .status('Y')
                .build();
    }

    public static NationalityUpdateRequestDTO nationalityUpdateRequestDTO() {
        return NationalityUpdateRequestDTO.builder()
                .id(1L)
                .name("Nepali")
                .status('Y')
                .remarks("update")
                .build();
    }

    public static NationalityMinimalResponseDTO getNationalityMinimalResponseDTO() {
        return NationalityMinimalResponseDTO.builder()
                .id(1L)
                .name("Nepali")
                .status('Y')
                .build();
    }

    public static Nationality getNationalityInfo() {
        Nationality nationality = new Nationality();
        nationality.setId(1L);
        nationality.setName("Chinese");
        nationality.setStatus('Y');

        return nationality;
    }

    public static List<NationalityDropDownResponseDTO> getDropDownList() {
        return Arrays.asList(NationalityDropDownResponseDTO
                .builder()
                .value(1L)
                .label("Nepali")
                .build());

    }

    public static NationalityResponseDTO getNationalityResponseDTO() {
        return NationalityResponseDTO.builder()
                .remarks("test")
                .build();
    }
}
