package com.cogent.admin.dto.response.profile;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileDropdownDTO implements Serializable {
    private Long value;

    private String label;
}
