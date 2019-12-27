package com.cogent.admin.dto.municipality;

import com.cogent.admin.dto.response.municipality.MunicipalityDropdownDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.persistence.model.Municipality;

import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.district.DistrictTestUtils.getDistrictInfo;

/**
 * @author smriti on 2019-09-15
 */
public class MunicipalityResponseUtils {
    public static Municipality getMunicipality() {
        return new Municipality(1L, "KATHMANDU", 'Y', null, getDistrictInfo());
    }

    public static Municipality getUpdatedMunicipality() {
        return new Municipality(1L, "KATHMANDU DISTRICT", 'Y', "update name!", getDistrictInfo());
    }

    public static Municipality getDeletedMunicipality() {
        return new Municipality(1L, "KATHMANDU", 'D', "Yes, Delete it!!", getDistrictInfo());
    }

    public static List<MunicipalityMinimalResponseDTO> fetchMunicipalityMinimalResponseDTO() {
        return Arrays.asList(MunicipalityMinimalResponseDTO.builder()
                        .id(1L)
                        .name("KATHMANDU")
                        .status('Y')
                        .districtName("Kathmandu")
                        .build(),
                MunicipalityMinimalResponseDTO.builder()
                        .id(2L)
                        .name("BHAKTAPUR")
                        .status('Y')
                        .districtName("Bhaktapur")
                        .build()
        );
    }

    public static MunicipalityResponseDTO fetchMunicipalityResponseDTO() {
        return new MunicipalityResponseDTO("update name!", 1L);
    }

    public static List<MunicipalityDropdownDTO> fetchMunicipalityForDropDown() {
        return Arrays.asList(MunicipalityDropdownDTO.builder()
                        .value(1L)
                        .label("KATHMANDU")
                        .build(),
                MunicipalityDropdownDTO.builder()
                        .value(2L)
                        .label("BHAKTAPUR")
                        .build()
        );
    }
}
