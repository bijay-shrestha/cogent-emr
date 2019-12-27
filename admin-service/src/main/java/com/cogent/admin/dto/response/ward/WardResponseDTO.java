package com.cogent.admin.dto.response.ward;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WardResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private Boolean has_Unit;

    private String unitNames;

    private String remarks;
}
