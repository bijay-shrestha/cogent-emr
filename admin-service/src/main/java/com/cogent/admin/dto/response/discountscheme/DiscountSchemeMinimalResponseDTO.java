package com.cogent.admin.dto.response.discountscheme;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DiscountSchemeMinimalResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private Double netDiscount;

    private Map<String, String> departmentDiscount;

    private Map<String, String> serviceDiscount;

    private Integer totalItems;
}
