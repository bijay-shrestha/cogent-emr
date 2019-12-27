package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;
import com.cogent.admin.dto.response.surname.SurnameDropdownDTO;
import com.cogent.admin.dto.response.surname.SurnameMinimalResponseDTO;
import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.persistence.model.Surname;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 2019-09-12
 */
public interface SurnameService {
    void save(SurnameRequestDTO requestDTO);

    void update(SurnameUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<SurnameMinimalResponseDTO> search(SurnameSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<SurnameDropdownDTO> fetchActiveSurnameForDropDown();

    Surname fetchSurname(Long id);

    SurnameResponseDTO fetchSurnameDetails(Long id);
}
