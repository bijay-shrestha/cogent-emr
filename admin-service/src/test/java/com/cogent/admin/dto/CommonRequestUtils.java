package com.cogent.admin.dto;

import com.cogent.admin.dto.commons.DeleteRequestDTO;

public class CommonRequestUtils {

    public static DeleteRequestDTO getInvalidInputForDelete() {
        return DeleteRequestDTO.builder()
                .id(null)
                .remarks(null)
                .status('M')
                .build();
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return new DeleteRequestDTO(1L, "Yes.. Delete it", 'D');
    }
}
