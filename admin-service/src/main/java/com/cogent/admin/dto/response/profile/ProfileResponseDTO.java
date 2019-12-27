package com.cogent.admin.dto.response.profile;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 7/15/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDTO implements Serializable{

    private String name;

    private String description;

    private Character status;

    private Long subDepartmentId;

    private String subDepartmentName;

    private String subDepartmentCode;

    private Long departmentId;

    private String departmentName;

    private String remarks;
}
