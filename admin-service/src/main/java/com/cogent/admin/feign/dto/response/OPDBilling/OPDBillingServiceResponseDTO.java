package com.cogent.admin.feign.dto.response.OPDBilling;

import com.cogent.persistence.model.*;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 9/23/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OPDBillingServiceResponseDTO implements Serializable {

    private BillingMode billingMode;

    private Referrer referrer;

    private Department department;

    private Hospital hospital;

    private DiscountScheme discountScheme;

    private Specialization specialization;

    private PaymentType paymentType;
}
