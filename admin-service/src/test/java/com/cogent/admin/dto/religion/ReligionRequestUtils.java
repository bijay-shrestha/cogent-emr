package com.cogent.admin.dto.request.religion;

public class ReligionRequestUtils {

    public static ReligionRequestDTO getInvalidInputForSave() {
        return ReligionRequestDTO.builder()
                .name("")
                .status('X')
                .build();
    }

    public static ReligionRequestDTO getReligionRequestDTO() {
        return ReligionRequestDTO.builder()
                .name("Hindusm")
                .status('Y')
                .build();
    }

    public static ReligionUpdateRequestDTO getInvalidInputForUpdate() {
        return ReligionUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('X')
                .build();
    }

    public static ReligionUpdateRequestDTO getReligionUpdateRequestDTO() {
        return ReligionUpdateRequestDTO.builder()
                .id(1L)
                .name("Hindusm")
                .status('N')
                .remarks("OK. Inactive it!")
                .build();
    }

    public static ReligionSearchRequestDTO getReligionSearchRequestDTO() {

        return ReligionSearchRequestDTO.builder()
                .name("Mr.")
                .status('Y')
                .build();
    }


}
