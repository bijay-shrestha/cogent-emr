package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.persistence.model.Provinces;

import java.util.Date;
import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class ProvincesUtils {
    public static Provinces convertToProvincesInfo(ProvincesRequestDTO provincesRequestDTO) {
        provincesRequestDTO.setName(toUpperCase(provincesRequestDTO.getName()));
        return MapperUtility.map(provincesRequestDTO, Provinces.class);
    }

    public static BiFunction<Provinces, DeleteRequestDTO, Provinces> updateProvinces = (serviceModeToUpdate
            , deleteRequestDTO) -> {
        serviceModeToUpdate.setStatus(deleteRequestDTO.getStatus());
        serviceModeToUpdate.setRemarks(deleteRequestDTO.getRemarks());
        serviceModeToUpdate.setLastModifiedDate(new Date());
        serviceModeToUpdate.setModifiedById(1L);

        return serviceModeToUpdate;
    };
}
