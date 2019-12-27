package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityDropdownDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.persistence.model.Municipality;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 2019-09-15
 */
public interface MunicipalityService {
    void save(MunicipalityRequestDTO requestDTO);

    void update(MunicipalityUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<MunicipalityMinimalResponseDTO> search(MunicipalitySearchRequestDTO searchRequestDTO, Pageable pageable);

    List<MunicipalityDropdownDTO> fetchActiveMunicipalityForDropDown();

    MunicipalityResponseDTO fetchDetailsById(Long id);

    Municipality fetchMunicipalityById(Long id);
}
