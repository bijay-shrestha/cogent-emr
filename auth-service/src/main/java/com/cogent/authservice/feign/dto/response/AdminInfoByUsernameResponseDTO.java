package com.cogent.authservice.feign.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author smriti ON 02/01/2020
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoByUsernameResponseDTO implements Serializable {

    private String password;

    private List<String> assignedApplicationModuleCodes;
}
