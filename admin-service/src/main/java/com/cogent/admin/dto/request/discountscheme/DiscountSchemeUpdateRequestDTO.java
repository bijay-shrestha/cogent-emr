package com.cogent.admin.dto.request.discountscheme;

import com.cogent.admin.constraintvalidator.Status;
import com.cogent.admin.dto.request.discountscheme.discountschemedetails.DepartmentDiscountRequestDTO;
import com.cogent.admin.dto.request.discountscheme.discountschemedetails.ServiceDiscountRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountSchemeUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String code;

    private List<DepartmentDiscountRequestDTO> departmentDiscountList;

    private List<ServiceDiscountRequestDTO> serviceDiscountList;

    private Double netDiscount;

    private Boolean has_netDiscount;

    @NotNull
    @Status
    private Character status;

    @NotNull
    @NotEmpty
    @NotBlank
    private String remarks;

}
