package com.cogent.admin.dto.response.subdepartment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubDepartmentMinimalResponseDTO implements Serializable {

    private Long id;

    private String department;

    private String name;

    private String code;

    private Character status;

    private Integer totalItems;

}
