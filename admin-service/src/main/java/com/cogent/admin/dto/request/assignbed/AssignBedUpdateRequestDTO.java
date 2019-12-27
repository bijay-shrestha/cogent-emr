package com.cogent.admin.dto.request.assignbed;

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
 * @author Sauravi Thapa 11/6/19
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignBedUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long wardId;

    private Long unitId;

    @NotNull
    private Long bedId;

    @NotNull
    @Status
    private Character status;

    @NotNull
    @NotBlank
    @NotEmpty
    private String remarks;
}
