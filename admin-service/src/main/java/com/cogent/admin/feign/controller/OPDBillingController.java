package com.cogent.admin.feign.controller;

import com.cogent.admin.feign.dto.request.OPDBilling.InsuranceRequestDTO;
import com.cogent.admin.feign.dto.request.OPDBilling.OPDBillingServiceRequestDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.InsuranceResponseDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.OPDBillingServiceResponseDTO;
import com.cogent.admin.feign.service.OPDBillingService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.MicroServiceConstants.BillingConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.DETAILS;

/**
 * @author Sauravi Thapa 12/13/19
 */
@RestController
@RequestMapping(API_V1 + BASE_OPD_BILLING)
public class OPDBillingController {

    private final OPDBillingService service;

    public OPDBillingController(OPDBillingService service) {
        this.service = service;
    }

    @PutMapping(ADMIN + DETAILS)
    public OPDBillingServiceResponseDTO getDetailsForBilling(@RequestBody OPDBillingServiceRequestDTO requestDTO) {
        return service.getDetailsForBilling(requestDTO);
    }

    @PutMapping(ADMIN +INSURANCE_COMPANY+ DETAILS)
    public InsuranceResponseDTO getInsuranceDetailsForBilling(@RequestBody InsuranceRequestDTO requestDTO) {
        return service.getInsuranceDetails(requestDTO);
    }


}
