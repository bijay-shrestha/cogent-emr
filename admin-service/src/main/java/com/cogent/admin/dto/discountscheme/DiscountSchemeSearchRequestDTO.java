package com.cogent.admin.dto.discountscheme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountSchemeSearchRequestDTO implements Serializable {

    private Long id;

    private Long departmentId;

    private Long serviceId;

    private String discountSchemeName;

    private String discountSchemeCode;

    private String departmentName;

    private String serviceName;

    private Integer netDiscount;

    private Character status;
}
