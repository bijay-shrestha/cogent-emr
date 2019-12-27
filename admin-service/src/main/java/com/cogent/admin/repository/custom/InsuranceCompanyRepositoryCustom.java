package com.cogent.admin.repository.custom;


import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyMinimalResponseDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("insuranceCompanyCustom")
public interface InsuranceCompanyRepositoryCustom {

    Optional<List<DropDownResponseDTO>> fetchActiveDropDownList();

    Optional<List<DropDownResponseDTO>> fetchDropDownList();

    List<InsuranceCompanyMinimalResponseDTO> searchInsuranceCompany(
            InsuranceCompanySearchRequestDTO searchRequestDTO,
            Pageable pageable);

    InsuranceCompanyResponseDTO fetchInsuranceCompanyDetails(Long id);

}
