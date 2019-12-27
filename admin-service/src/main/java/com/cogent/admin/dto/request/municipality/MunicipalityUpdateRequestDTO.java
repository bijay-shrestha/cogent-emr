package com.cogent.admin.dto.request.municipality;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-09-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String name;

    @NotNull
    @Status
    private Character status;

    @NotNull
    private Long districtId;

    @NotNull
    @NotEmpty
    private String remarks;
}
