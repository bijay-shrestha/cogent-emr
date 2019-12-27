package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.response.unit.UnitMinimalResponseDTO;
import com.cogent.admin.dto.response.unit.UnitResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 10/13/19
 */

@Repository
@Qualifier("unitRepositoryCustom")
public interface UnitRepositoryCustom {

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();

    List<Object[]> fetchUnitByNameOrCode(String name, String code);

    List<Object[]> checkUnitNameAndCodeIfExist(Long id,String name, String code);

    List<UnitMinimalResponseDTO> searchUnit(
            UnitSearchRequestDTO searchRequestDTO, Pageable pageable);

    UnitResponseDTO fetchUnitDetails(Long id);

}
