package com.cogent.admin.dto.request.admin;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smriti on 2019-08-30
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String fullName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String mobileNumber;

    @NotNull
    @Status
    private Character status;

    @NotNull
    private Character hasMacBinding;

    @NotNull
    private Long adminCategoryId;

    @NotNull
    @NotEmpty
    private String remarks;

    private List<MacAddressInfoUpdateRequestDTO> macAddressInfoUpdateRequestDTOS;

    @NotEmpty
    private List<AdminApplicationModuleUpdateRequestDTO> adminApplicationModuleUpdateRequestDTOS;

    @NotEmpty
    private List<AdminProfileUpdateRequestDTO> adminProfileUpdateRequestDTOS;
}
