package com.cogent.admin.dto.response.adminCategory;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategoryDropdownDTO implements Serializable {

    private Long value;

    private String label;
}
