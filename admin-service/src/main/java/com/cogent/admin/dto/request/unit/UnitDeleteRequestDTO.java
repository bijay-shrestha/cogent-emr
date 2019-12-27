package com.cogent.admin.dto.request.unit;

import com.cogent.admin.constraintvalidator.DeleteStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitDeleteRequestDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private Long wardId;

    @NotNull
    @NotEmpty
    private String remarks;

    @NotNull
    @DeleteStatus
    private Character status;

}
