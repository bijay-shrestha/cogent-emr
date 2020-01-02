package com.cogent.authservice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author smriti ON 02/01/2020
 */
@Getter
@Setter
public class LoginRequestDTO implements Serializable {

    private String username;

    private String password;

    private String subDepartmentCode;
}
