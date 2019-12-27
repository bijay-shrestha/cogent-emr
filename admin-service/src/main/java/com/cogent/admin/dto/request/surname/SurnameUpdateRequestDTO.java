package com.cogent.admin.dto.request.surname;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-09-12
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurnameUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Status
    private Character status;

    @NotNull
    private Long ethnicityId;

    @NotNull
    @NotEmpty
    private String remarks;
}
