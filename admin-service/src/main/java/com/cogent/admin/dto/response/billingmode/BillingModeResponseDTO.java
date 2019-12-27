package com.cogent.admin.dto.response.billingmode;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/4/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BillingModeResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private String description;

    private String remarks;
}
