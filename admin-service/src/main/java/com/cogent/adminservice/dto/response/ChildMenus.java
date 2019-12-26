package com.cogent.adminservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Rupak
 */
@Getter
@Setter
public class ChildMenus {
    Long userMenuId;
    List<Long> roleId;
}

