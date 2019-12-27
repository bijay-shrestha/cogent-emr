package com.cogent.admin.dto.response.servicecharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingModeDropDownResponseDTO implements Serializable {

    private BigInteger value;

    private String label;

    private Double price;
}
