package com.cogent.admin.dto.response.discountscheme.discountschemedetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/15/19
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDiscountResponseDTO implements Serializable {

    private String serviceName;

    private  Double serviceDiscount;
}
