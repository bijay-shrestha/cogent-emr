package com.cogent.admin.dto.response.subdepartment;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class SubDepartmentResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private DropDownResponseDTO dropDownResponseDTO;

    private String remarks;

    private Date createdDate;

    private Date lastModifiedDate;

    private Long createdById;

    private Long modifiedById;
}
