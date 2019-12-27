package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyUpdateRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyMinimalResponseDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import com.cogent.persistence.model.InsuranceCompany;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InsuranceCompanyService {

    void createInsuranceCompany(InsuranceCompanyRequestDTO requestDTO);

    void deleteInsuranceCompany(DeleteRequestDTO deleteRequestDTO);

    void updateInsuranceCompany(InsuranceCompanyUpdateRequestDTO updateRequestDTO);

    List<InsuranceCompanyMinimalResponseDTO> searchInsuranceCompany(InsuranceCompanySearchRequestDTO searchRequestDTO,
                                                                    Pageable pageable);

    InsuranceCompanyResponseDTO fetchInsuranceCompanyDetails(Long id);

    InsuranceCompany fetchInsuranceCompany(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();


}
