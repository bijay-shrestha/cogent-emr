package com.cogent.admin.dto.request.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;

    private Long departmentId;

    private Long subDepartmentId;

    private Long serviceTypeId;

}
