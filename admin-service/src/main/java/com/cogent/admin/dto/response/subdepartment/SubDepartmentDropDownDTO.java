package com.cogent.admin.dto.response.subdepartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubDepartmentDropDownDTO implements Serializable {

    private Long value;

    private String label;


    private String code;
}
