package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.district.DistrictRequestDTO;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.request.district.DistrictUpdateRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DistrictService {

    void createDistrict(DistrictRequestDTO districtRequestDTO);

    List<DistrictDropDownResponseDTO> districtDropdown();

    List<DistrictDropDownResponseDTO> activeDistrictDropdown();

    DistrictResponseDTO fetchDistrictDetails(Long id);

    List<DistrictMinimalResponseDTO> searchDistrict(
            DistrictSearchRequestDTO districtSearchRequestDTO,
            Pageable pageable);

    void deleteDistrict(DeleteRequestDTO deleteRequestDTO);

    void updateDistrict(DistrictUpdateRequestDTO updateRequestDTO);
}
