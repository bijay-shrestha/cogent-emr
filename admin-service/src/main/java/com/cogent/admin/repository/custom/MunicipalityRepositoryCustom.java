package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityDropdownDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.persistence.model.Municipality;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-09-15
 */
@Repository
@Qualifier("municipalityRepositoryCustom")
public interface MunicipalityRepositoryCustom {
    Long fetchMunicipalityByName(String name);

    Municipality findMunicipality(Long id);

    Long findMunicipalityByIdAndName(Long id, String name);

    List<MunicipalityMinimalResponseDTO> search(MunicipalitySearchRequestDTO searchRequestDTO, Pageable pageable);

    List<MunicipalityDropdownDTO> fetchActiveMunicipalityForDropDown();

    MunicipalityResponseDTO fetchDetailsById(Long id);

    Municipality fetchMunicipalityById(Long id);
}
