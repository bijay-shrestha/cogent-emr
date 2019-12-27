package com.cogent.admin.dto.request.applicationModules;

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
public class ApplicationModuleSearchRequestDTO implements Serializable {

    private String name;

    private Long subDepartmentId;
}
