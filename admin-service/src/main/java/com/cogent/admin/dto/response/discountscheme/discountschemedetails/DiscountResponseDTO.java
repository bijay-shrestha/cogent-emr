package com.cogent.admin.dto.response.discountscheme.discountschemedetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/12/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private Double netDiscount;

    private String departmentName;

    private  String departmentDiscount;

    private String serviceName;

    private  String serviceDiscount;
}
