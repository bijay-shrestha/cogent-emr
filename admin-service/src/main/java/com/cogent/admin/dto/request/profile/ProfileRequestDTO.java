package com.cogent.admin.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author smriti on 7/4/19
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDTO implements Serializable {

    @Valid
    private ProfileDTO profileDTO;

    @Valid
    private List<ProfileMenuRequestDTO> profileMenuRequestDTO;
}
