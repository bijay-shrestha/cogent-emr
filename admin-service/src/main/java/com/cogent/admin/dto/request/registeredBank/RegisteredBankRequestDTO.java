package com.cogent.admin.dto.request.registeredBank;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankRequestDTO implements Serializable {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String code;

    @NotNull
    @NotEmpty
    private String contact;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String swiftCode;

    @NotNull
    @Status
    private Character status;
}
