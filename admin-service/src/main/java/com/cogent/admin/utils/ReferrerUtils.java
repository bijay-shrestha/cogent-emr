package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;
import com.cogent.persistence.model.Referrer;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Rupak
 */
public class ReferrerUtils {

    public static Referrer convertDTOToReferrer(ReferrerRequestDTO referrerRequestDTO) {
        referrerRequestDTO.setName(toUpperCase(referrerRequestDTO.getName()));
        return MapperUtility.map(referrerRequestDTO, Referrer.class);
    }

    public static Referrer convertToUpdatedReferrer(ReferrerUpdateRequestDTO updateRequestDTO,
                                                    Referrer referrer) {

        referrer.setName(toUpperCase(updateRequestDTO.getName()));
        referrer.setStatus(updateRequestDTO.getStatus());
        referrer.setRemarks(updateRequestDTO.getRemarks());

        return referrer;
    }

    public static Referrer convertToDeletedReferrer(Referrer referrer, DeleteRequestDTO deleteRequestDTO) {

        referrer.setStatus(deleteRequestDTO.getStatus());
        referrer.setRemarks(deleteRequestDTO.getRemarks());
        return referrer;

    }


}
