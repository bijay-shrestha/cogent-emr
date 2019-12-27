package com.cogent.admin.dto.response.admin;

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
public class AdminProfileResponseDTO implements Serializable {

    private Long adminProfileId;

    private Long profileId;
}
