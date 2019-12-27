package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;
import com.cogent.persistence.model.Specialization;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-09-25
 */
public class SpecializationUtils {
    public static Specialization convertDTOToSpecialization(SpecializationRequestDTO requestDTO) {

        Specialization specialization = new Specialization();
        specialization.setName(toUpperCase(requestDTO.getName()));
        specialization.setStatus(requestDTO.getStatus());
        return specialization;
    }

    public static Specialization convertToUpdatedSpecialization(SpecializationUpdateRequestDTO updateRequestDTO,
                                                                Specialization specialization) {

        specialization.setName(toUpperCase(updateRequestDTO.getName()));
        specialization.setStatus(updateRequestDTO.getStatus());
        specialization.setRemarks(updateRequestDTO.getRemarks());
        return specialization;
    }

    public static Specialization convertToDeletedSpecialization(Specialization specialization,
                                                                DeleteRequestDTO deleteRequestDTO) {

        specialization.setStatus(deleteRequestDTO.getStatus());
        specialization.setRemarks(deleteRequestDTO.getRemarks());
        return specialization;
    }
}
