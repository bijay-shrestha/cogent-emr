package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.Country;

import java.util.List;

/**
 * @author smriti on 08/11/2019
 */
public interface CountryService {
    List<DropDownResponseDTO> fetchActiveCountry();

    Country fetchCountryById(Long id);
}
