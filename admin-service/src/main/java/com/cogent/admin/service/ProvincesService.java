package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesMinimalResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProvincesService {

    void createProvinces(ProvincesRequestDTO provincesRequestDTO);

    List<ProvincesDropDownResponseDTO> provincesDropdown();

    List<ProvincesDropDownResponseDTO> activeProvinceDropdown();

    List<ProvincesMinimalResponseDTO> searchProvinces(
            ProvincesSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    ProvincesResponseDTO fetchProvincesDetails(Long id);

    void deleteProvinces(DeleteRequestDTO deleteRequestDTO);

    void updateProvinces(ProvincesUpdateRequestDTO provincesUpdateRequestDTO);
}
