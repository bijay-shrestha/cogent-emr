package com.cogent.admin.dto.request.adminCategory;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-08-11
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategoryRequestDTO implements Serializable {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String code;

    @NotNull
    @NotEmpty
    private String designation;

    @NotNull
    @NotEmpty
    private String registrationNumber;

    @NotNull
    @Status
    private Character status;
}
