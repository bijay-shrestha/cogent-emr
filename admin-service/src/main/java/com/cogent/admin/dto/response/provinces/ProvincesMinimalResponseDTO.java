package com.cogent.admin.dto.response.provinces;

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
public class ProvincesMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;

    private Integer totalItems;
}

