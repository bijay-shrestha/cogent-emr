package com.cogent.admin.feign.utils;

import com.cogent.admin.feign.dto.response.OPDBilling.InsuranceResponseDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.OPDBillingServiceResponseDTO;
import com.cogent.persistence.model.*;

/**
 * @author Sauravi Thapa 12/13/19
 */
public class OPDBillingUtils {

    public static OPDBillingServiceResponseDTO parseToOPDBillingResponseDTO(BillingMode billingMode,
                                                                            Referrer referrer,
                                                                            Department department,
                                                                            Hospital hospital,
                                                                            DiscountScheme discountScheme,
                                                                            Specialization specialization,
                                                                            PaymentType paymentType){


        return OPDBillingServiceResponseDTO
                .builder()
                .billingMode(billingMode)
                .referrer(referrer)
                .department(department)
                .hospital(hospital)
                .discountScheme(discountScheme)
                .specialization(specialization)
                .paymentType(paymentType)
                .build();
    }

    public static InsuranceResponseDTO parseToInsuranceResponseDTO(InsuranceCompany insuranceCompany){


        return InsuranceResponseDTO
                .builder()
                .insuraceCompany(insuranceCompany)
                .build();
    }
}
