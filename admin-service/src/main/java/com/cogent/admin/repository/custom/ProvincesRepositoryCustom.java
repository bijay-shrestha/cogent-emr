package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesMinimalResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author Sauravi  on 2019-08-26
 */

@Repository
@Qualifier("provincesRepositoryCustom")
public interface ProvincesRepositoryCustom {

    Long fetchCountByName(String name);

    Optional<List<ProvincesDropDownResponseDTO>> fetchDropDownList();

    Optional<List<ProvincesDropDownResponseDTO>> fetchActiveDropDownList();

    List<ProvincesMinimalResponseDTO> searchProvinces(
            ProvincesSearchRequestDTO serviceModeSearchRequestDTO,
            Pageable pageable);

    ProvincesResponseDTO fetchProvincesDetails(Long id);
}
