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
public class AssignedRoleResponseDTO {

    private Long parentId;
    private List<ChildMenus> childMenus;


}
