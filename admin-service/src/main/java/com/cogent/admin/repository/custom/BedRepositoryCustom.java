package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.response.bed.BedMinimalResponseDTO;
import com.cogent.admin.dto.response.bed.BedResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/1/19
 */

@Repository
@Qualifier("bedRepositoryCustom")
public interface BedRepositoryCustom {
    List<Object[]> findBedByNameOrCode(String name, String code);

    List<Object[]> checkBedNameAndCodeIfExist(Long id,String name, String code);

    List<BedMinimalResponseDTO> searchBed(
            BedSearchRequestDTO searchRequestDTO, Pageable pageable);

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();

    BedResponseDTO fetchBedDetails(Long id);
}
