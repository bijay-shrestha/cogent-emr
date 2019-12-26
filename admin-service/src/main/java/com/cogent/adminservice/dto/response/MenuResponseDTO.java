package com.cogent.adminservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Rupak
 */

@Getter
@Setter
public class MenuResponseDTO {

    private List<Long> parentId;
    private List<Long>  userMenuId;
    private List<Long>  roleId;
}
