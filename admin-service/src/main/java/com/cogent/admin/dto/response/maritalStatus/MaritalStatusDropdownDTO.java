package com.cogent.admin.dto.response.maritalStatus;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaritalStatusDropdownDTO {

    private Long value;

    private String label;
}
