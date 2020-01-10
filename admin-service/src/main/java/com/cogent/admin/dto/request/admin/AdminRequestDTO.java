package com.cogent.admin.dto.request.admin;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smriti on 6/25/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequestDTO implements Serializable {

    @NotNull
    @NotEmpty
    private String fullName;

    @NotNull
    @NotEmpty
    private String username;

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
    private Long hospitalId;

    @NotNull
    private List<Long> applicationModuleIds;

    @NotEmpty
    private List<Long> profileIds;

    private List<String> macAddressInfoRequestDTOS;
}
