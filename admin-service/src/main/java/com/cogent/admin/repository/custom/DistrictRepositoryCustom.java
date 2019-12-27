package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import com.cogent.persistence.model.District;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("districtRepositoryCustom")
public interface DistrictRepositoryCustom {

    Long fetchDistrictCountByName(String name);

    Optional<List<DistrictDropDownResponseDTO>> fetchDropDownList();

    Optional<List<DistrictDropDownResponseDTO>> fetchActiveDropDownList();

    DistrictResponseDTO fetchDistrictDetails(Long id);

    List<DistrictMinimalResponseDTO> searchDistrict(
            DistrictSearchRequestDTO districtSearchRequestDTO,
            Pageable pageable);

    Optional<District> fetchDistrictById(Long id);

    List<District> fetchDistrictByProvincesId(Long provincesId);

    List<DeleteRequestDTO> fetchDistrictToDeleteByProvinceId(Long provinceId);
}
