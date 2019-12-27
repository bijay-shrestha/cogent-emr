package com.cogent.admin.dto.response.municipality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityResponseDTO implements Serializable {

    private String remarks;

    private Long districtId;
}
