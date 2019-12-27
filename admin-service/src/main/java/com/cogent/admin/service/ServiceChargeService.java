package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.admin.dto.response.servicecharge.BillingModeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 11/18/19
 */
public interface ServiceChargeService {

    void createServiceCharge(ServiceChargeRequestDTO requestDTO);

    void deleteServiceCharge(DeleteRequestDTO requestDTO);

    void updateServiceCharge(ServiceChargeUpdateRequestDTO requestDTO);

    List<ServiceChargeMinimalResponseDTO> searchServiceCharge(ServiceChargeSearchRequestDTO requestDTO,
                                                              Pageable pageable);

    ServiceChargeResponseDTO fetchServiceChargeDetailsById(Long id);

    List<ServiceChargeDropDownResponseDTO> fetchDropDownList();

    List<ServiceChargeDropDownResponseDTO> fetchActiveDropDownList();

    List<BillingModeDropDownResponseDTO> fetchActiveDropDownListByBillingModeId(Long billingModeId);


}


