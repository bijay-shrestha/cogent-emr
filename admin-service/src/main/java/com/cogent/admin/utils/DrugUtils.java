package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.persistence.model.Drug;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class DrugUtils {
    public static Drug convertToDrug(DrugRequestDTO drugRequestDTO) {
        drugRequestDTO.setName(toUpperCase(drugRequestDTO.getName()));
        drugRequestDTO.setCode(toUpperCase(drugRequestDTO.getCode()));
        return MapperUtility.map(drugRequestDTO, Drug.class);
    }

    public static BiFunction<Drug, DeleteRequestDTO, Drug> updateDrug = (serviceModeToUpdate
            , deleteRequestDTO) -> {
        serviceModeToUpdate.setStatus(deleteRequestDTO.getStatus());
        serviceModeToUpdate.setRemarks(deleteRequestDTO.getRemarks());
        return serviceModeToUpdate;
    };

}
