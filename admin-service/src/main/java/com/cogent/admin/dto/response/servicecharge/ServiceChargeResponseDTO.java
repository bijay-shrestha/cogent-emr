package com.cogent.admin.dto.response.servicecharge;

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
public class ServiceChargeResponseDTO implements Serializable {

    private String name;

    private String code;

    private String department;

    private String subDepartment;

    private String billingMode;

    private String serviceType;

    private Double price;

    private Character status;

    private String remarks;


}
