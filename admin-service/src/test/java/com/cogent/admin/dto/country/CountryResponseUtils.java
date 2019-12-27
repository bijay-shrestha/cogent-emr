package com.cogent.admin.dto.country;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.Country;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 08/11/2019
 */
public class CountryResponseUtils {

    public static List<DropDownResponseDTO> fetchActiveCountries() {
        return Arrays.asList(
                DropDownResponseDTO.builder()
                        .value(1L)
                        .label("NEPAL")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("INDIA")
                        .build()
        );
    }

    public static Country fetchCountry() {
        return new Country(1L, "NEPAL","Nepal","NP", "NPL",
                524,977, 'Y');
    }

}
