package com.cogent.admin.dto.referrer;

import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;

/**
 * @author Rupak
 */
public class ReferrerTestUtils {

    public static ReferrerRequestDTO getInvalidInputForSave() {
        return ReferrerRequestDTO.builder()
                .name("@")
                .status('M')
                .build();
    }

    public static ReferrerRequestDTO getReferrerRequestDTO() {
        return ReferrerRequestDTO.builder()
                .name("Dr. Rupak Chaulagain")
                .status('Y')
                .build();
    }

    public static ReferrerUpdateRequestDTO getInvalidInputForUpdate() {
        return ReferrerUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .build();
    }

    public static ReferrerUpdateRequestDTO getReferrerUpdateRequestDTO() {
        return ReferrerUpdateRequestDTO.builder()
                .id(1L)
                .name("Dr. Rupak Chaulagain")
                .status('N')
                .remarks("Ok. Inactive it.")
                .build();
    }

    public static ReferrerSearchRequestDTO getReferrerSearchRequestDTO() {
        return ReferrerSearchRequestDTO.builder()
                .name("Dr. Rupak Chaulagain")
                .status('Y')
                .build();
    }

}
