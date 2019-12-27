package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 10/2/19
 */

@Repository
@Qualifier("wardRepositoryCustom")
public interface WardRepositoryCustom {

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();

    List<Object[]> fetchWardByNameOrCode(String name, String code);

    List<Object[]> checkIfWardNameAndCodeExists(Long id, String name, String code);

    List<WardMinimalResponseDTO> searchWard(
            WardSearchRequestDTO searchRequestDTO, Pageable pageable);

    WardResponseDTO fetchWardDetails(Long id);
}
