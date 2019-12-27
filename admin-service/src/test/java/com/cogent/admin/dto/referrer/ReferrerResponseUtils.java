package com.cogent.admin.dto.referrer;

import com.cogent.admin.dto.response.referrer.ReferrerDropdownDTO;
import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import com.cogent.persistence.model.Referrer;

import java.util.Arrays;
import java.util.List;

public class ReferrerResponseUtils {
    
    public static Referrer getReferrer() {
        return new Referrer(1L, "Dr. Rupak Chaulagain", 'Y', null);
    }

    public static Referrer getDeletedReferrer() {
        return new Referrer(1L, "Dr. Rupak Chaulagain", 'D', "Yes, Delete it!!");
    }

    public static List<ReferrerResponseDTO> fetchReferrerResponseDTO() {
        return Arrays.asList(ReferrerResponseDTO.builder()
                        .id(1L)
                        .name("Dr. Rupak Chaulagain")
                        .status('Y')
                        .build(),
                ReferrerResponseDTO.builder()
                        .id(2L)
                        .name("Er.")
                        .status('Y')
                        .build()
        );
    }

    public static List<ReferrerDropdownDTO> fetchReferrerForDropDown() {

        return Arrays.asList(ReferrerDropdownDTO.builder()
                        .value(1L)
                        .label("Dr.")
                        .build(),
                ReferrerDropdownDTO.builder()
                        .value(2L)
                        .label("Er.")
                        .build()
        );
    }

    }
