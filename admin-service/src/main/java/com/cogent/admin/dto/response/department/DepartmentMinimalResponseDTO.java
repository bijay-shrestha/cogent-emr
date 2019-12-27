package com.cogent.admin.dto.response.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;

    private String remarks;

    private Integer totalItems;

}
