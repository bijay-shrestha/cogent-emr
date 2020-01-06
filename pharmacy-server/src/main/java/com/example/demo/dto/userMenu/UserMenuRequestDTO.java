package com.example.demo.dto.userMenu;

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
