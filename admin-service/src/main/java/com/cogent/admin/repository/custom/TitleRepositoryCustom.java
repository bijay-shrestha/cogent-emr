package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.response.title.TitleDropdownDTO;
import com.cogent.admin.dto.response.title.TitleResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("titleRepositoryCustom")
public interface TitleRepositoryCustom {

    Long fetchTitleByName(String name);

    Long findTitleByIdAndName(Long id, String name);

    List<TitleResponseDTO> search(TitleSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<TitleDropdownDTO> fetchTitleForDropDown();
}
