package com.cogent.admin.dto.insurancecompany;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyUpdateRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyMinimalResponseDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import com.cogent.persistence.model.InsuranceCompany;

import java.util.Arrays;
import java.util.List;

public class InsuranceCompanyTestUtils {
    public static InsuranceCompanyRequestDTO getInsuranceCompanyRequestDTO() {
        return InsuranceCompanyRequestDTO.builder()
                .name("Thapa Insurance Company")
                .status('Y')
                .build();
    }

    public static InsuranceCompanySearchRequestDTO getInsuranceCompanySeacrhRequestDTO() {
        return InsuranceCompanySearchRequestDTO.builder()
                .id(1L)
                .name("Thapa Insurance Company")
                .status('Y')
                .build();
    }

    public static InsuranceCompanyUpdateRequestDTO getInsuranceCompanyUpdateRequestDTO() {
        return InsuranceCompanyUpdateRequestDTO.builder()
                .id(1L)
                .name("Thapa Insurance Company")
                .status('Y')
                .remarks("update")
                .build();
    }

    public static InsuranceCompanyMinimalResponseDTO getInsuranceCompanyMinimalResponseDTO() {
        return InsuranceCompanyMinimalResponseDTO.builder()
                .id(1L)
                .name("Thapa Insurance Company")
                .status('Y')
                .build();
    }

    public static InsuranceCompany getInsuranceCompanyInfo() {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.setId(1L);
        insuranceCompany.setName("Thapa Insurance Company");
        insuranceCompany.setStatus('Y');

        return insuranceCompany;
    }

    public static List<DropDownResponseDTO> getDropDownList() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Thapa Insurance Compnay")
                .build());

    }

    public static InsuranceCompanyResponseDTO getInsuranceCompanyResponseDTO() {
        return InsuranceCompanyResponseDTO.builder()
                .remarks("test")
                .build();
    }
}
