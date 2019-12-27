package com.cogent.admin.dto.request.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentSearchRequestDTO {
    private Long id;


    private String name;


    private String code;


    private Character status;

}
