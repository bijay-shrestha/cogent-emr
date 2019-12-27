package com.cogent.admin.dto.response.profile;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author smriti on 7/15/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDetailResponseDTO implements Serializable {

    private ProfileResponseDTO profileResponseDTO;

    private Map<Long, List<ProfileMenuResponseDTO>> profileMenuResponseDTOS;
}
