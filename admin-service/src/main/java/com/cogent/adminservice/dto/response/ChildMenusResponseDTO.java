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
public class ChildMenusResponseDTO implements Serializable {

    private Long parentId;

    private Long userMenuId;

    private List<Long> roleId;
}