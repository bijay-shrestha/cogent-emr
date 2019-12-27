package com.cogent.admin.dto.response.doctor;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author smriti on 2019-09-27
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorMinimalResponseDTO implements Serializable {
    private BigInteger doctorId;

    private String doctorName;

    private String code;

    private String mobileNumber;

    private Character status;

    private String specializationName;

    private String departmentName;

    private String avatarUri;

    private Character isAvatarDefault;

    private int totalItems;
}
