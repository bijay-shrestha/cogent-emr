package com.cogent.admin.dto.response.service;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ServiceResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private String departmentName;

    private String subDepartmentName;

    private String serviceTypeName;

    private String remarks;

}
