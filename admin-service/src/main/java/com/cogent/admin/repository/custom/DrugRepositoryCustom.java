package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.response.drug.DrugMinimalResponseDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("drugRepositoryCustom")
public interface DrugRepositoryCustom {

    List<Object[]> fetchDrugByNameOrCode(String name, String code);

    List<Object[]> checkIfDrugNameAndCodeExists( Long id,  String name,String code);

    Optional<List<DropDownResponseDTO>> fetchDropDownList();

    Optional<List<DropDownResponseDTO>> fetchActiveDropDownList();

    DrugResponseDTO fetchDrugDetails(Long id);

    List<DrugMinimalResponseDTO> searchDrug(
            DrugSearchRequestDTO drugSearchRequestDTO,
            Pageable pageable);
}
