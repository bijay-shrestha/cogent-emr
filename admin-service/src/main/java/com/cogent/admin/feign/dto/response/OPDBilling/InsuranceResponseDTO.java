package com.cogent.admin.feign.dto.response.OPDBilling;

import com.cogent.persistence.model.InsuranceCompany;
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
public class InsuranceResponseDTO implements Serializable {

    private InsuranceCompany insuraceCompany;
}
