package com.cogent.admin.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smriti on 2019-09-11
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDTO implements Serializable {

    @NotNull
    private ProfileUpdateDTO profileDTO;

    @NotEmpty
    private List<ProfileMenuUpdateRequestDTO> profileMenuRequestDTO;
}
