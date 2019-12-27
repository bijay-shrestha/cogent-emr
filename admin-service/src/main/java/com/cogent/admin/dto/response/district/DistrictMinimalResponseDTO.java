package com.cogent.admin.dto.response.district;

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
public class DistrictMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;

    private String province;

    private Integer totalItems;
}
