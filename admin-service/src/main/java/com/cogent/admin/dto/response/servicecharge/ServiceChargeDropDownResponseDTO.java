package com.cogent.admin.dto.response.servicecharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Sauravi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceChargeDropDownResponseDTO implements Serializable {

    private BigInteger value;

    private String label;

    private String billingMode;

    private Double price;
}
