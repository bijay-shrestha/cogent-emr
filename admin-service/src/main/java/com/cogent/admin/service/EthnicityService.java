package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityUpdateRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityMinimalResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import com.cogent.persistence.model.Ethnicity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi  on 2019-08-25
 */

public interface EthnicityService {

    void createEthnicity(EthnicityRequestDTO ethnicityRequestDTO);

    void deleteEthnicity(DeleteRequestDTO deleteRequestDTO);

    void updateEthnicity(EthnicityUpdateRequestDTO ethnicityUpdateRequestDTO);

    EthnicityResponseDTO fetchEthnicityDetails(Long id);

    Ethnicity fetchEthnicity(Long id);

    List<EthnicityDropDownResponseDTO> fetchActiveDropDownList();

    List<EthnicityDropDownResponseDTO> fetchDropDownList();

    List<EthnicityMinimalResponseDTO> searchEthnicity(
            EthnicitySearchRequestDTO ethnicitySearchRequestDTO,
            Pageable pageable);
}
