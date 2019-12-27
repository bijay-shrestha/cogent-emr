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
public class ServiceChargeMinimalResponseDTO implements Serializable {

    private String name;

    private String code;

    private Double price;

    private String billingMode;

    private Character status;

    private Integer totalItems;
}
