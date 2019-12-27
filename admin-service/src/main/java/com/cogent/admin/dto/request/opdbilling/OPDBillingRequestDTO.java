package com.cogent.admin.dto.request.opdbilling;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 9/23/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OPDBillingRequestDTO implements Serializable {

    private Long nationalityId;

    private Long municipalityId;

    private Long surnameId;

    private  Long religionId;

    private Long maritalStatusId;

    private Long titleId;

    private Long categoryId;

}
