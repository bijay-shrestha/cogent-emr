package com.cogent.admin.dto.request.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author smriti ON 14/01/2020
 */
@Getter
@Setter
@Builder
public class AdminProfileRequestDTO implements Serializable {

    private Long applicationModuleId;

    private Long profileId;
}
