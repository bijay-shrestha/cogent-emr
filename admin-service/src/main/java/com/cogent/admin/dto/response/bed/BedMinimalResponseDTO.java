package com.cogent.admin.dto.response.bed;

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
public class BedMinimalResponseDTO implements Serializable {

    private String name;

    private String code;

    private String remarks;

    private Character status;

    private Integer totalItems;
}
