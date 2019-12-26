package com.cogent.adminservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Rupak
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JSONOBJ {

    private String subDepartmentCode;

//    Map<Long, Map<Long, Set<Long>>> assignedRoles;
//    Map<String, Map<String, Set<String>>> assignedRoles;

    List<ChildMenus> childMenus;

//    List<AssignedRoleResponseDTO> assignedRoles;

//    private MenuResponseDTO menuResponseDTO;


}
