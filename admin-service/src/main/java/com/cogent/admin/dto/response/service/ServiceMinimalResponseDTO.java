package com.cogent.admin.dto.response.service;

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
public class ServiceMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;

    private String remarks;

    private Integer totalItems;
}
