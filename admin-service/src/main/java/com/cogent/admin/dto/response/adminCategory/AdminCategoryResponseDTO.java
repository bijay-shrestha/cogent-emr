package com.cogent.admin.dto.response.adminCategory;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-08-11
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategoryResponseDTO implements Serializable {

    private String name;

    private String code;

    private String designation;

    private String registrationNumber;

    private Character status;

    private String remarks;
}
