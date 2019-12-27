package com.cogent.admin.dto.request.discountscheme;

import com.cogent.admin.constraintvalidator.Status;
import com.cogent.admin.dto.request.discountscheme.discountschemedetails.DepartmentDiscountRequestDTO;
import com.cogent.admin.dto.request.discountscheme.discountschemedetails.ServiceDiscountRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountSchemeRequestDTO implements Serializable {

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    private String code;

    @NotNull
    @Status
    private Character status;

    private Boolean has_netDiscount;

    private List<DepartmentDiscountRequestDTO> departmentDiscountList;

    private List<ServiceDiscountRequestDTO> serviceDiscountList;

    @DecimalMax("100.0") @DecimalMin("0.0")
    private Double netDiscount;

}
