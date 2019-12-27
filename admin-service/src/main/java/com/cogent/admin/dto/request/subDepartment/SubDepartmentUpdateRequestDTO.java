package com.cogent.admin.dto.request.subDepartment;

import com.cogent.admin.constraintvalidator.Status;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class SubDepartmentUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long departmentId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String code;

    @NotNull
    @Status
    private Character status;

    @NotNull
    @NotEmpty
    @NotBlank
    private String remarks;


}
