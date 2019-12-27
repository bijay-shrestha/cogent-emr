package com.cogent.admin.dto.title;

import com.cogent.admin.dto.response.title.TitleDropdownDTO;
import com.cogent.admin.dto.response.title.TitleResponseDTO;
import com.cogent.persistence.model.Title;

import java.util.Arrays;
import java.util.List;

public class TitleResponseUtils {

    public static Title getTitle() {
        return new Title(1L, "Mr.", 'Y', null);
    }

    public static Title getDeletedTitle() {
        return new Title(1L, "Mr.", 'D', "Yes, Delete it!!");
    }

    public static List<TitleResponseDTO> fetchTitleResponseDTO() {
        return Arrays.asList(TitleResponseDTO.builder()
                        .id(1L)
                        .name("Mr.")
                        .status('Y')
                        .build(),
                TitleResponseDTO.builder()
                        .id(2L)
                        .name("Er.")
                        .status('Y')
                        .build()
        );
    }

    public static List<TitleDropdownDTO> fetchTitleForDropDown() {

        return Arrays.asList(TitleDropdownDTO.builder()
                        .value(1L)
                        .label("Dr.")
                        .build(),
                TitleDropdownDTO.builder()
                        .value(2L)
                        .label("Er.")
                        .build()
        );
    }
}
