package com.cogent.admin.dto.request.login;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti ON 27/12/2019
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuRequestDTO implements Serializable {

    private String username;

    private String subDepartmentCode;
}
