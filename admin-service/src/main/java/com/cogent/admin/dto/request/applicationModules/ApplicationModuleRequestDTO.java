package com.cogent.admin.dto.request.applicationModules;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti ON 24/12/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModuleRequestDTO implements Serializable {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Long subDepartmentId;

    @Status
    private Character status;
}
