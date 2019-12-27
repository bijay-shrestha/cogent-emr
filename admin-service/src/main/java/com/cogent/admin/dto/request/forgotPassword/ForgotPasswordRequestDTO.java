package com.cogent.admin.dto.request.forgotPassword;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-09-22
 */
@Data
public class ForgotPasswordRequestDTO implements Serializable {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
}
