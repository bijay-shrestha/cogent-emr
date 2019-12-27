package com.cogent.admin.dto.request.profile;

import com.cogent.admin.constraintvalidator.Status;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 7/8/19
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileMenuRequestDTO implements Serializable {

    @NotNull
    private Long parentId;

    @NotNull
    private Long userMenuId;

    @NotNull
    private Long roleId;

    @NotNull
    @Status
    private Character status;
}
