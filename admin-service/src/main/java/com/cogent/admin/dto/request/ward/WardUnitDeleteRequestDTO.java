package com.cogent.admin.dto.request.ward;

import com.cogent.admin.constraintvalidator.DeleteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 10/21/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardUnitDeleteRequestDTO implements Serializable {

    @NotNull
    private Long wardId;

    @NotNull
    private Long unitID;

    @NotNull
    @DeleteStatus
    private Character status;

    @NotNull
    @NotEmpty
    @NotBlank
    private String remarks;
}
