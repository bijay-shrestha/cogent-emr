package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.persistence.model.Ethnicity;

import java.util.Date;
import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class EthnicityUtils {
    public static Ethnicity convertToEthnicityInfo(EthnicityRequestDTO ethnicityRequestDTO) {
        ethnicityRequestDTO.setName(toUpperCase(ethnicityRequestDTO.getName()));
        ethnicityRequestDTO.setCode(toUpperCase(ethnicityRequestDTO.getCode()));
        return MapperUtility.map(ethnicityRequestDTO, Ethnicity.class);
    }

    public static BiFunction<Ethnicity, DeleteRequestDTO, Ethnicity> updateEthnicity = (ethnicityToUpdate, deleteRequestDTO) -> {
        ethnicityToUpdate.setStatus(deleteRequestDTO.getStatus());
        ethnicityToUpdate.setRemarks(deleteRequestDTO.getRemarks());
        ethnicityToUpdate.setLastModifiedDate(new Date());
        ethnicityToUpdate.setModifiedById(1L);

        return ethnicityToUpdate;
    };

}
