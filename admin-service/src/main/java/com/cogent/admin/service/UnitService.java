package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitRequestDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.admin.dto.response.unit.UnitMinimalResponseDTO;
import com.cogent.admin.dto.response.unit.UnitResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 10/13/19
 */
public interface UnitService {

    void createUnit(UnitRequestDTO requestDTO);

    void deleteUnit(DeleteRequestDTO deleteRequestDTO);

    void updateUnit(UnitUpdateRequestDTO updateRequestDTO);

    List<UnitMinimalResponseDTO> searchUnit(
            UnitSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    UnitResponseDTO fetchUnitDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();
}
