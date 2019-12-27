package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.title.TitleRequestDTO;
import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.request.title.TitleUpdateRequestDTO;
import com.cogent.admin.dto.response.title.TitleDropdownDTO;
import com.cogent.admin.dto.response.title.TitleResponseDTO;
import com.cogent.persistence.model.Title;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TitleService {

    void save(TitleRequestDTO requestDTO);

    void update(TitleUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<TitleResponseDTO> search(TitleSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<TitleDropdownDTO> fetchTitleForDropDown();

    Title fetchTitle(Long id);
}
