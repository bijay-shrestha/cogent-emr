package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityRequestDTO;
import com.cogent.persistence.model.Nationality;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author sauravi on 2019-09-19
 */
public class NationalityUtils {

    public static Nationality convertToNationalityInfo(NationalityRequestDTO nationalityRequestDTO) {
        nationalityRequestDTO.setName(toUpperCase(nationalityRequestDTO.getName()));
        return MapperUtility.map(nationalityRequestDTO, Nationality.class);
    }


    public static BiFunction<Nationality, DeleteRequestDTO, Nationality> updateNationality = (nationalityToUpdate, deleteRequestDTO) -> {
        nationalityToUpdate.setStatus(deleteRequestDTO.getStatus());
        nationalityToUpdate.setRemarks(deleteRequestDTO.getRemarks());

        return nationalityToUpdate;
    };
}
