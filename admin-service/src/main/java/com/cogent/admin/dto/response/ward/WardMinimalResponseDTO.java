package com.cogent.admin.dto.response.ward;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WardMinimalResponseDTO implements Serializable {

    private String name;

    private String code;

    private String remarks;

    private Character status;

    private Boolean has_Unit;

    private String unitNames;

    private Integer totalItems;
}
