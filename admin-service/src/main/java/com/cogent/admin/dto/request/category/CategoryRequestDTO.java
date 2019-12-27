package com.cogent.admin.dto.request.category;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 10/24/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDTO implements Serializable {


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
