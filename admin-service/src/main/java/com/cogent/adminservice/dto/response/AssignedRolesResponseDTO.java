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
public class AssignedRolesResponseDTO implements Serializable {

    private Long parentId;

    private List<ChildMenusResponseDTO> childMenus;
}

