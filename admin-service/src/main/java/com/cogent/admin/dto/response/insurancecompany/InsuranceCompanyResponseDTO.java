package com.cogent.admin.dto.response.insurancecompany;

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
public class InsuranceCompanyResponseDTO implements Serializable {

    private String name;

    private Character status;

    private String remarks;

}
