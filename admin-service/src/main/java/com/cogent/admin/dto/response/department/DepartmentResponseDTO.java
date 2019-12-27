package com.cogent.admin.dto.response.department;

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
public class DepartmentResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private Date createdDate;

    private Date lastModifiedDate;

    private Long createdById;

    private Long modifiedById;

    private String remarks;

}
