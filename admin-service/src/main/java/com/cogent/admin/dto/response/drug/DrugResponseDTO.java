package com.cogent.admin.dto.response.drug;

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
public class DrugResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private String remarks;

}
