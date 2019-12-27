package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.title.TitleRequestDTO;
import com.cogent.admin.dto.request.title.TitleUpdateRequestDTO;
import com.cogent.persistence.model.Title;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class TitleUtils {
    public static Title convertDTOToTitle(TitleRequestDTO titleRequestDTO) {
        titleRequestDTO.setName(toUpperCase(titleRequestDTO.getName()));
        return MapperUtility.map(titleRequestDTO, Title.class);
    }

    public static Title convertToUpdatedTitle(TitleUpdateRequestDTO updateRequestDTO,
                                              Title title) {

        title.setName(toUpperCase(updateRequestDTO.getName()));
        title.setStatus(updateRequestDTO.getStatus());
        title.setRemarks(updateRequestDTO.getRemarks());

        return title;
    }

    public static Title convertToDeletedTitle(Title title, DeleteRequestDTO deleteRequestDTO) {

        title.setStatus(deleteRequestDTO.getStatus());
        title.setRemarks(deleteRequestDTO.getRemarks());
        return title;

    }

}
