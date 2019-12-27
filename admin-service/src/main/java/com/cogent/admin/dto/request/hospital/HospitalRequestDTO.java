package com.cogent.admin.dto.request.hospital;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalRequestDTO implements Serializable {

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String name;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String code;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String hospitalAddress;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String hospitalPanNumber;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String hospitalPhone;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String hospitalLogo;

    @NotNull
    @Status
    private Character status;

    private String remarks;

}
