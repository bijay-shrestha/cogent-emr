package com.cogent.email.dto.request.admin;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author smriti on 2019-08-19
 */
@Getter
@Setter
public class AdminRequestDTO implements Serializable {
    private String username;

    private String email;
}
