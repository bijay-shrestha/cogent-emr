package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.response.referrer.ReferrerDropdownDTO;
import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rupak
 */
@Repository
@Qualifier("referrerRepositoryCustom")
public interface ReferrerRepositoryCustom {

    Long fetchReferrerByName(String name);

    Long findReferrerByIdAndName(Long id, String name);

    List<ReferrerResponseDTO> search(ReferrerSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<ReferrerDropdownDTO> fetchReferrerForDropDown();

}
