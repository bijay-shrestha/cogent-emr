package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.persistence.model.DoctorCategory;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class DoctorCategoryUtils {
    public static DoctorCategory convertToDoctorCategory(DoctorCategoryRequestDTO requestDTO) {
        requestDTO.setName(toUpperCase(requestDTO.getName()));
        requestDTO.setCode(toUpperCase(requestDTO.getCode()));
        return MapperUtility.map(requestDTO, DoctorCategory.class);
    }

    public static BiFunction<DoctorCategory, DeleteRequestDTO, DoctorCategory> deleteDoctorCategory = (doctorCategoryToUpdate
            , deleteRequestDTO) -> {
        doctorCategoryToUpdate.setStatus(deleteRequestDTO.getStatus());
        doctorCategoryToUpdate.setRemarks(deleteRequestDTO.getRemarks());
        return doctorCategoryToUpdate;
    };

    public static BiFunction<DoctorCategory, DoctorCategoryUpdateRequestDTO, DoctorCategory> updateDoctorCategory = (doctorCategoryToUpdate
            , requestDTO) -> {
        doctorCategoryToUpdate.setName(toUpperCase(requestDTO.getName()));
        doctorCategoryToUpdate.setCode(toUpperCase(requestDTO.getCode()));
        doctorCategoryToUpdate.setStatus(requestDTO.getStatus());
        doctorCategoryToUpdate.setRemarks(requestDTO.getRemarks());
        return doctorCategoryToUpdate;
    };
}
