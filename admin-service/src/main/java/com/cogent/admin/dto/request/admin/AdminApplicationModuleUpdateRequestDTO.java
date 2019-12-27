package com.cogent.admin.dto.request.admin;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti ON 25/12/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminApplicationModuleUpdateRequestDTO implements Serializable {

    private Long adminApplicationModuleId;

    private Long applicationModuleId;

    private Character status;
}
