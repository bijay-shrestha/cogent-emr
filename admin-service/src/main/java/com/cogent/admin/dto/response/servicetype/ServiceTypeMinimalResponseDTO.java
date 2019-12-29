package com.cogent.admin.dto.response.servicetype;

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
public class ServiceTypeMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;

    private Integer totalItems;
}