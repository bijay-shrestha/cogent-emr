package com.cogent.admin.dto.request.admin;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-08-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPasswordRequestDTO implements Serializable {
    @NotNull
    @NotEmpty
    private String token;

    @NotNull
    @NotEmpty
    private String password;
}
