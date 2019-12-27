package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionUpdateRequestDTO;
import com.cogent.persistence.model.Religion;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class ReligionUtils {
    public static Religion convertDTOToReligion(ReligionRequestDTO religionRequestDTO) {
        religionRequestDTO.setName(toUpperCase(religionRequestDTO.getName()));
        return MapperUtility.map(religionRequestDTO, Religion.class);
    }

    public static Religion convertToUpdatedReligion(ReligionUpdateRequestDTO updateRequestDTO,
                                                    Religion religion) {

        religion.setName(toUpperCase(updateRequestDTO.getName()));
        religion.setStatus(updateRequestDTO.getStatus());
        religion.setRemarks(updateRequestDTO.getRemarks());

        return religion;
    }

    public static Religion convertToDeletedReligion(Religion religion, DeleteRequestDTO deleteRequestDTO) {

        religion.setStatus(deleteRequestDTO.getStatus());
        religion.setRemarks(deleteRequestDTO.getRemarks());
        return religion;

    }

}
