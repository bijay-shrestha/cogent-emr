package com.cogent.admin.dto.request.doctor;

import com.cogent.admin.constraintvalidator.SpecialCharacters;
import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author smriti on 2019-09-27
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDTO implements Serializable {

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
    @Email
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

    @NotEmpty
    private List<Long> specializationIds;

    @NotEmpty
    private List<Long> departmentIds;

    @NotEmpty
    private List<Long> doctorTypeIds;

    @NotEmpty
    private List<Long> qualificationIds;
}
