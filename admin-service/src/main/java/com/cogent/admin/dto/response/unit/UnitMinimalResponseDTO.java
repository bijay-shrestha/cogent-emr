package com.cogent.admin.dto.response.unit;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UnitMinimalResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private Integer totalItems;
}
