package com.cogent.admin.dto.response.municipality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String districtName;

    private Character status;

    private Integer totalItems;
}
