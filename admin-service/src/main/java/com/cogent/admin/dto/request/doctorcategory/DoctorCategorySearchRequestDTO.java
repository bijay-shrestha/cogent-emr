package com.cogent.admin.dto.request.doctorcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorCategorySearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;
}
