package com.cogent.admin.dto.request.title;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TitleRequestDTO {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Status
    private Character status;
}