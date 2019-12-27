package com.cogent.admin.dto.response.assignbed;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/4/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AssignBedMinimalResponseDTO implements Serializable {

    private String bedName;

    private String wardName;

    private String unitName;

    private Character status;

    private String remarks;

    private Integer totalItems;
}
