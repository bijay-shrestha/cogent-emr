package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeUpdateRequestDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeMinimalResponseDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 12/4/19
 */
public interface BillingModeService {

    void createBillingMode(BillingModeRequestDTO requestDTO);

    void deleteBillingMode(DeleteRequestDTO deleteRequestDTO);

    void updateBillingMode(BillingModeUpdateRequestDTO updateRequestDTO);

    List<BillingModeMinimalResponseDTO> searchBillingMode(
            BillingModeSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    BillingModeResponseDTO fetchBillingModeDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();
}
