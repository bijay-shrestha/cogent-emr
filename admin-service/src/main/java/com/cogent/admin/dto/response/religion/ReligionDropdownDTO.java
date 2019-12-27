package com.cogent.admin.dto.response.religion;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReligionDropdownDTO implements Serializable {

    private Long value;

    private String label;
}
