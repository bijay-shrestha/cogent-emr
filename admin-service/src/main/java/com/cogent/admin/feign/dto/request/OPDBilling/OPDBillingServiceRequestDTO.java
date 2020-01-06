package com.cogent.admin.feign.dto.request.OPDBilling;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 9/23/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
OPDBillingServiceRequestDTO implements Serializable {

    private Long billingModeId;

    private Long referrerId;

    private Long departmentId;

    private  Long hospitalId;

    private Long discountSchemeId;

    private Long specializationId;

    private Long paymentTypeId;
}
