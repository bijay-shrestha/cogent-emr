package com.cogent.admin.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-11
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileSearchRequestDTO implements Serializable {

    private String name;

    private Character status;

    private Long subDepartmentId;

    private Long departmentId;
}
