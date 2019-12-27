package com.cogent.admin.dto.request.bed;

import com.cogent.admin.constraintvalidator.Status;
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
public class BedRequestDTO implements Serializable {

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String code;

    @NotNull
    @Status
    private Character status;

}
