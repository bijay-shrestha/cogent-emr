package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionUpdateRequestDTO;
import com.cogent.admin.dto.response.religion.ReligionDropdownDTO;
import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import com.cogent.persistence.model.Religion;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReligionService {

    void save(ReligionRequestDTO requestDTO);

    void update(ReligionUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<ReligionResponseDTO> search(ReligionSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<ReligionDropdownDTO> fetchReligionForDropDown();

    Religion fetchReligion(Long id);
}
