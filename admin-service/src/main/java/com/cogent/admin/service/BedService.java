package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedRequestDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.admin.dto.response.bed.BedMinimalResponseDTO;
import com.cogent.admin.dto.response.bed.BedResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 11/1/19
 */
public interface BedService {

    void createBed(BedRequestDTO bedRequestDTO);

    void deleteBed(DeleteRequestDTO deleteRequestDTO);

    void updateBed(BedUpdateRequestDTO updateRequestDTO);

    List<BedMinimalResponseDTO> searchBed(
            BedSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    BedResponseDTO fetchBedDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();

}
