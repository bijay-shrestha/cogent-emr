package com.cogent.admin.feign.dto.request.OPDBilling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 12/17/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceRequestDTO implements Serializable {
    private Long insuraceCompanyId;
}
