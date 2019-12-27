package com.cogent.admin.dto.response.applicationModules;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti ON 24/12/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModuleDetailResponseDTO implements Serializable {

    private String name;

    private Long subDepartmentId;

    private String subDepartmentName;

    private Character status;

    private String remarks;
}
