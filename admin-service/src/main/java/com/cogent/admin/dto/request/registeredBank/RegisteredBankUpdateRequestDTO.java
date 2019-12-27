package com.cogent.admin.dto.request.registeredBank;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 12/10/19
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

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

    @NotNull
    @NotEmpty
    private String remarks;


}
