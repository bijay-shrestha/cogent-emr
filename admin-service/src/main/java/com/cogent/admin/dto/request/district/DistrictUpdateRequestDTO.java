package com.cogent.admin.dto.request.district;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long provincesId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;


    @NotNull
    @Status
    private Character status;

    @NotNull
    @NotEmpty
    @NotBlank
    private String remarks;

}
