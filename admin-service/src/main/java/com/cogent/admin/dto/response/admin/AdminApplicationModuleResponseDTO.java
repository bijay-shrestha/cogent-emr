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
public class AdminApplicationModuleResponseDTO implements Serializable {

    private Long adminApplicationModuleId;

    private Long applicationModuleId;
}
