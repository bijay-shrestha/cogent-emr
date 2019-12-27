package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.response.surname.SurnameDropdownDTO;
import com.cogent.admin.dto.response.surname.SurnameMinimalResponseDTO;
import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.persistence.model.Surname;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-09-12
 */
@Repository
@Qualifier("surnameRepositoryCustom")
public interface SurnameRepositoryCustom {
    Long fetchSurnameByName(String name);

    Long findSurnameByIdAndName(Long id, String name);

    List<SurnameMinimalResponseDTO> search(SurnameSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<SurnameDropdownDTO> fetchActiveSurnameForDropDown();

    SurnameResponseDTO fetchDetailsById(Long id);

    Surname fetchSurname(Long id);
}
