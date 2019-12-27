package com.cogent.admin.dto.response.doctor;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author smriti on 14/11/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateResponseDTO implements Serializable {

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

    private String avatarUri;

    private Character isAvatarDefault;

    private String signatureUri;

    private Character isSignatureDefault;

    private String remarks;

    private BigInteger genderId;

    private BigInteger countryId;

    private String doctorSpecializationId;

    private String specializationId;

    private String doctorDepartmentId;

    private String departmentId;

    private String doctorScheduleTypeId;

    private String doctorTypeId;

    private String doctorQualificationId;

    private String qualificationId;

    /*doctorSpecializationId, specializationId*/
    private Map<Long, Long> doctorSpecializationResponseDTOs;

    /*doctorDepartmentId, departmentId*/
    private Map<Long, Long> doctorDepartmentResponseDTOs;

    /*doctorScheduleTypeId, DoctorTypeId*/
    private Map<Long, Long> doctorScheduleTypeResponseDTOs;

    /*doctorQualificationId, qualificationId*/
    private Map<Long, Long> doctorQualificationResponseDTOs;
}
