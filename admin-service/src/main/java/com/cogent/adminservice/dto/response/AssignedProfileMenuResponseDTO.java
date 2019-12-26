package com.cogent.adminservice.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rupak
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