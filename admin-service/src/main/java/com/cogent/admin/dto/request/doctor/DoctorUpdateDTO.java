package com.cogent.admin.dto.request.doctor;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti on 2019-09-29
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    @SpecialCharacters
    private String name;

    @NotNull
    @NotEmpty
    private String mobileNumber;

    @NotNull
    private Date dateOfBirth;

    @NotNull
    @NotEmpty
    private String nepaliDateOfBirth;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String nmcNumber;

    @NotNull
    private Date joinedDate;

    @NotNull
    @NotEmpty
    private String nepaliJoinedDate;

    @NotNull
    private Long genderId;

    @NotNull
    private Long countryId;

    @NotNull
    @Status
    private Character status;

    private Date resignationDate;

    private String nepaliResignationDate;

    @NotNull
    @NotEmpty
    private String remarks;
}
