package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusDropdownDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import com.cogent.persistence.model.MaritalStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaritalStatusService {
    void save(MaritalStatusRequestDTO requestDTO);

    void update(MaritalStatusUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<MaritalStatusResponseDTO> search(MaritalStatusSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<MaritalStatusDropdownDTO> fetchMaritalStatusForDropDown();

    MaritalStatus fetchMaritalStatus(Long id);

}
