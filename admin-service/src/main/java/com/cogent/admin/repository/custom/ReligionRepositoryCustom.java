package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.response.religion.ReligionDropdownDTO;
import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("religionRepositoryCustom")
public interface ReligionRepositoryCustom {
    Long fetchReligionByName(String name);

    Long findReligionByIdAndName(Long id, String name);

    List<ReligionResponseDTO> search(ReligionSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<ReligionDropdownDTO> fetchReligionForDropDown();

}
