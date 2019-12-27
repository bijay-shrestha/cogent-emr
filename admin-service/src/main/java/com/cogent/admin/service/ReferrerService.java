package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;
import com.cogent.admin.dto.response.referrer.ReferrerDropdownDTO;
import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import com.cogent.persistence.model.Referrer;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Rupak
 */
public interface ReferrerService {

    void save(ReferrerRequestDTO requestDTO);

    void update(ReferrerUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<ReferrerResponseDTO> search(ReferrerSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<ReferrerDropdownDTO> fetchReferrerForDropDown();

    Referrer fetchReferrer(Long id);

}
