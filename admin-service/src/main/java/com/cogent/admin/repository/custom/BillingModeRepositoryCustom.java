package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeMinimalResponseDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 12/4/19
 */

@Repository
@Qualifier("billingModeRepositoryCustom")
public interface BillingModeRepositoryCustom {
    List<Object[]> findBillingModeByNameOrCode(String name, String code);

    List<BillingModeMinimalResponseDTO> searchBillingMode(
            BillingModeSearchRequestDTO searchRequestDTO, Pageable pageable);

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();

    BillingModeResponseDTO fetchBillingModeDetails(Long id);

    List<Object[]> checkIfBillingModeNameAndCodeExists(Long id, String name, String code);


}
