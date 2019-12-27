package com.cogent.admin.dto.response.doctorcategory;

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
public class DoctorCategoryMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;

    private Integer totalItems;
}