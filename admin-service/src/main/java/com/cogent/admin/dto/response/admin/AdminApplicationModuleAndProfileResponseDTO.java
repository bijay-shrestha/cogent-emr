package com.cogent.admin.dto.response.admin;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti ON 26/12/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminApplicationModuleAndProfileResponseDTO implements Serializable {

    /*APPLICATION MODULE RESPONSE*/
    private Long adminApplicationModuleId;

    private Long applicationModuleId;

    private String applicationModuleName;

    /*PROFILE RESPONSE*/
    private Long adminProfileId;

    private Long profileId;

    private String profileName;
}
