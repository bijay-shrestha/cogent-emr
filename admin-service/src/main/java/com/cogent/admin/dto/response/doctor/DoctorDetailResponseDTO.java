package com.cogent.admin.dto.response.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author smriti on 2019-09-30
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorDetailResponseDTO implements Serializable {

    private BigInteger doctorId;

    private String doctorName;

    private String mobileNumber;

    private String code;

    private Character status;

    private String email;

    private String nmcNumber;

    private String nepaliDateOfBirth;

    private String nepaliJoinedDate;

    private String nepaliResignationDate;

    private String genderName;

    private String countryName;

    private String specializationName;

    private String departmentName;

    private String doctorTypeName;

    private String qualificationName;

    private String avatarUri;

    private Character isAvatarDefault;

    private String signatureUri;

    private Character isSignatureDefault;

    private String remarks;
}
