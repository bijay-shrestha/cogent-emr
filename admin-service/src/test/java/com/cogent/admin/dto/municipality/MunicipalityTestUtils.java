package com.cogent.admin.dto.municipality;

import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;

/**
 * @author smriti on 2019-09-15
 */
public class MunicipalityTestUtils {

    public static MunicipalityRequestDTO getInvalidInputForSave() {
        return MunicipalityRequestDTO.builder()
                .name("@@")
                .status('M')
                .districtId(1L)
                .build();
    }

    public static MunicipalityRequestDTO getMunicipalityRequestDTO() {
        return MunicipalityRequestDTO.builder()
                .name("Kathmandu")
                .status('Y')
                .districtId(1L)
                .build();
    }

    public static MunicipalityUpdateRequestDTO getInvalidInputForUpdate() {
        return MunicipalityUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .districtId(1L)
                .build();
    }

    public static MunicipalityUpdateRequestDTO getMunicipalityUpdateRequestDTO() {
        return MunicipalityUpdateRequestDTO.builder()
                .id(1L)
                .name("Kathmandu District")
                .status('Y')
                .districtId(1L)
                .remarks("update name!")
                .build();
    }

    public static MunicipalitySearchRequestDTO getMunicipalitySearchRequestDTO() {
        return MunicipalitySearchRequestDTO.builder()
                .name("Kathmandu")
                .status('Y')
                .districtId(1L)
                .build();
    }
}
