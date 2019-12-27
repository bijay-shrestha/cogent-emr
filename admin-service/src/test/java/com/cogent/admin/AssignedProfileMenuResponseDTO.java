package com.cogent.admin;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author smriti ON 19/12/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignedProfileMenuResponseDTO implements Serializable {

    private String subDepartmentName;

    private String subDepartmentCode;

    private List<AssignedRolesResponseDTO> assignedRolesResponseDTOS;
}
