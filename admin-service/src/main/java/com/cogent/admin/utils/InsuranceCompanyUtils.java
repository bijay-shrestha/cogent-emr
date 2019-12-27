package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyRequestDTO;
import com.cogent.persistence.model.InsuranceCompany;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author sauravi on 2019-09-20
 */
public class InsuranceCompanyUtils {

    public static InsuranceCompany convertToInsuranceCompanyInfo(InsuranceCompanyRequestDTO requestDTO) {
        requestDTO.setName(toUpperCase(requestDTO.getName()));
        return MapperUtility.map(requestDTO, InsuranceCompany.class);
    }


    public static BiFunction<InsuranceCompany, DeleteRequestDTO, InsuranceCompany> updateInsuranceCompany = (insuranceCompanyToUpdate, deleteRequestDTO) -> {
        insuranceCompanyToUpdate.setStatus(deleteRequestDTO.getStatus());
        insuranceCompanyToUpdate.setRemarks(deleteRequestDTO.getRemarks());

        return insuranceCompanyToUpdate;
    };
}
