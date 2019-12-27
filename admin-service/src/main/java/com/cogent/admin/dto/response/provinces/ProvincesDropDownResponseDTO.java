package com.cogent.admin.dto.response.provinces;

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
public class ProvincesDropDownResponseDTO implements Serializable {

    private Long value;

    private String label;
}
