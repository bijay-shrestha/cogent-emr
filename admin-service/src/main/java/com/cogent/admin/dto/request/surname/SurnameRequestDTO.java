package com.cogent.admin.dto.request.surname;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-09-12
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurnameRequestDTO implements Serializable {

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String name;

    @NotNull
    @Status
    private Character status;

    @NotNull
    private Long ethnicityId;
}
