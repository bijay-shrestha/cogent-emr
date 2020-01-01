package com.cogent.admin.dto.response.admin;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author smriti ON 01/01/2020
 */
@Getter
@Setter
public class AdminInfoResponseDTO implements Serializable {

    private Long adminId;

    private String username;

    private String fullName;

    private Long subDepartmentId;

    private String subDepartmentName;

    private String profileName;
}
