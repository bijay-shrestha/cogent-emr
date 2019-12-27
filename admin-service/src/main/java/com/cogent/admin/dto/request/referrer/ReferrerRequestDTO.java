package com.cogent.admin.dto.request.referrer;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Rupak
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferrerRequestDTO {

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String name;

    @NotNull
    @Status
    private Character status;

}
