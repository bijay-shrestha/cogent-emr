package com.cogent.admin.dto.request.admin;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 17/11/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileUpdateRequestDTO implements Serializable {

    private Long adminProfileId;

    private Long profileId;

    private Character status;
}
