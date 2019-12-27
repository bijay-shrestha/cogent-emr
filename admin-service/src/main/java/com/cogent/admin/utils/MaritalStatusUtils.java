package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;
import com.cogent.persistence.model.MaritalStatus;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class MaritalStatusUtils {
    public static MaritalStatus convertDTOToMaritalStatus(MaritalStatusRequestDTO maritalStatusRequestDTO) {
        maritalStatusRequestDTO.setName(toUpperCase(maritalStatusRequestDTO.getName()));
        return MapperUtility.map(maritalStatusRequestDTO, MaritalStatus.class);
    }

    public static MaritalStatus convertToUpdatedMaritalStatus(MaritalStatusUpdateRequestDTO updateRequestDTO,
                                                              MaritalStatus maritalStatus) {

        maritalStatus.setName(toUpperCase(updateRequestDTO.getName()));
        maritalStatus.setStatus(updateRequestDTO.getStatus());
        maritalStatus.setRemarks(updateRequestDTO.getRemarks());

        return maritalStatus;
    }

    public static MaritalStatus convertToDeletedMaritalStatus(MaritalStatus maritalStatus, DeleteRequestDTO deleteRequestDTO) {

        maritalStatus.setStatus(deleteRequestDTO.getStatus());
        maritalStatus.setRemarks(deleteRequestDTO.getRemarks());
        return maritalStatus;

    }
}
