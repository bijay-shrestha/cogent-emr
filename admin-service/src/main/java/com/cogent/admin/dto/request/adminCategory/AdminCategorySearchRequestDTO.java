package com.cogent.admin.dto.request.adminCategory;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-09
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCategorySearchRequestDTO implements Serializable {

    private Long id;

    private String code;

    private String designation;

    private Character status;
}
