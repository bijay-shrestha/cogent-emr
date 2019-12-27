package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.response.nationality.NationalityDropDownResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityMinimalResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("natioalityRepositoryCustom")
public interface NationalityRepositoryCustom {

    Optional<List<NationalityDropDownResponseDTO>> fetchActiveDropDownList();

    Optional<List<NationalityDropDownResponseDTO>> fetchDropDownList();

    List<NationalityMinimalResponseDTO> searchNationality(
            NationalitySearchRequestDTO searchRequestDTO,
            Pageable pageable);

    NationalityResponseDTO fetchNationalityDetails(Long id);
}
