package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusDropdownDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("maritalStatusRepositoryCustom")
public interface MaritalStatusRepositoryCustom {
    Long fetchMaritalStatusByName(String name);

    Long findMaritalStatusByIdAndName(Long id, String name);

    List<MaritalStatusResponseDTO> search(MaritalStatusSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<MaritalStatusDropdownDTO> fetchMaritalStatusForDropDown();

}
