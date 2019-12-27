package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardRequestDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 10/2/19
 */
public interface WardService {

    void createWard(WardRequestDTO requestDTO);

    void deleteWard(DeleteRequestDTO deleteRequestDTO);

    void updateWard(WardUpdateRequestDTO updateRequestDTO);

    List<WardMinimalResponseDTO> searchWard(
            WardSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    WardResponseDTO fetchWardDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();

}
