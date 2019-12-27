package com.cogent.admin.dto.response.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Rupak
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalDropdownDTO implements Serializable {

    private Long value;

    private String label;
}
