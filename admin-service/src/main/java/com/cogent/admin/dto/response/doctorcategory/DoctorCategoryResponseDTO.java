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
public class DoctorCategoryResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private String remarks;

}
