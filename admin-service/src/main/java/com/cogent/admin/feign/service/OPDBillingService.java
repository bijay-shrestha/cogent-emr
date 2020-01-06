package com.cogent.admin.feign.service;

import com.cogent.admin.feign.dto.request.OPDBilling.InsuranceRequestDTO;
import com.cogent.admin.feign.dto.request.OPDBilling.OPDBillingServiceRequestDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.InsuranceResponseDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.OPDBillingServiceResponseDTO;

/**
 * @author Sauravi Thapa 12/13/19
 */
public interface OPDBillingService {

    OPDBillingServiceResponseDTO getDetailsForBilling(OPDBillingServiceRequestDTO serviceRequestDTO);

    InsuranceResponseDTO getInsuranceDetails(InsuranceRequestDTO requestDTO);

}
