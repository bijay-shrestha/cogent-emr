package com.cogent.admin.dto.discountscheme.discountschemedetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/12/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDiscountRequestDTO implements Serializable {

    private Long departmentId;

    @DecimalMax("100.0") @DecimalMin("0.0")
    private  Double departmentDiscount;
}
