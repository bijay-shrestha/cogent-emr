package com.cogent.admin.dto.response.title;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TitleDropdownDTO {

    private Long value;

    private String label;
}
