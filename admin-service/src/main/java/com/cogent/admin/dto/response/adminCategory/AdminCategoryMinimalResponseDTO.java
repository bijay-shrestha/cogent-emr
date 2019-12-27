package com.cogent.admin.dto.response.adminCategory;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-08-11
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategoryMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String designation;

    private String registrationNumber;

    private Character status;

    private Integer totalItems;
}
