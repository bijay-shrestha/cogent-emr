package com.cogent.admin.dto.commons;

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
public class DeleteRequestDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String remarks;

    @NotNull
    @DeleteStatus
    private Character status;

}
