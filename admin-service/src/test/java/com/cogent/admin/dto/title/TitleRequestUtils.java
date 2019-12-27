package com.cogent.admin.dto.title;

import com.cogent.admin.dto.request.title.TitleRequestDTO;
import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.request.title.TitleUpdateRequestDTO;

public class TitleRequestUtils {

    public static TitleRequestDTO getInvalidInputForSave() {
        return TitleRequestDTO.builder()
                .name("")
                .status('X')
                .build();
    }

    public static TitleRequestDTO getTitleRequestDTO() {
        return TitleRequestDTO.builder()
                .name("Mr.")
                .status('Y')
                .build();
    }

    public static TitleUpdateRequestDTO getInvalidInputForUpdate() {
        return TitleUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('X')
                .build();
    }

    public static TitleUpdateRequestDTO getTitleUpdateRequestDTO() {
        return TitleUpdateRequestDTO.builder()
                .id(1L)
                .name("Mr.")
                .status('N')
                .remarks("OK. Inactive it!")
                .build();
    }

    public static TitleSearchRequestDTO getTitleSearchRequestDTO() {

        return TitleSearchRequestDTO.builder()
                .name("Mr.")
                .status('Y')
                .build();
    }


}
