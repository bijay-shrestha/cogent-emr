package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityMinimalResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author Sauravi  on 2019-08-26
 */

@Repository
@Qualifier("ethnicityRepositoryCustom")
public interface EthnicityRepositoryCustom {

    Optional<List<EthnicityDropDownResponseDTO>> dropDownList();

    Optional<List<EthnicityDropDownResponseDTO>> activeDropDownList();

    List<EthnicityMinimalResponseDTO> searchEthnicity(
            EthnicitySearchRequestDTO ethnicitySearchRequestDTO, Pageable pageable);

    List<Object[]> fetchEthnicityByNameOrCode(String name, String code);

    List<Object[]> checkEthnicityNameAndCodeIfExist(Long id,String name, String code);

    EthnicityResponseDTO fetchEthnicityDetails(Long id);

}
