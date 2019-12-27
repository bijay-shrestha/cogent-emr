package com.cogent.admin.dto.request.insurancecompany;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceCompanyRequestDTO {

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @Status
    private Character status;
}
