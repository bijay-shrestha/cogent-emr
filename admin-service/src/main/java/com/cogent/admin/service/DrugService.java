package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.request.drug.DrugUpdateRequestDTO;
import com.cogent.admin.dto.response.drug.DrugMinimalResponseDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DrugService {

    void createDrug(DrugRequestDTO drugRequestDTO);

    List<DropDownResponseDTO> drugDropdown();

    List<DropDownResponseDTO> activeDrugDropdown();

    DrugResponseDTO fetchDrugDetails(Long id);

    void deleteDrug(DeleteRequestDTO deleteRequestDTO);

    List<DrugMinimalResponseDTO> searchDrug(
            DrugSearchRequestDTO drugSearchRequestDTO,
            Pageable pageable);

    void updateDrug(DrugUpdateRequestDTO drugUpdateRequestDTO);
}
