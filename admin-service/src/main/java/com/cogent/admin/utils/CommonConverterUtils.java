package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;

import java.util.function.BiFunction;

public class CommonConverterUtils {
    public static final BiFunction<String, Character, DeleteRequestDTO> ConvertToDeleteRequestDTO =
            (remarks, status) -> {
                DeleteRequestDTO deleteRequestDTO = new DeleteRequestDTO();
                deleteRequestDTO.setStatus(status);
                deleteRequestDTO.setRemarks(remarks);

                return deleteRequestDTO;
            };



}
