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
public class MunicipalityDropdownDTO implements Serializable {

    private Long value;

    private String label;
}
