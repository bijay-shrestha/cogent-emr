package com.cogent.admin.dto.response.specialization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-25
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecializationResponseDTO implements Serializable {

    private String name;

    private Character status;

    private String remarks;
}
