package com.cogent.admin.dto.response.login;

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
public class ChildMenusResponseDTO implements Serializable {

    private Long parentId;

    private Long userMenuId;

    private List<Long> roleId;
}
