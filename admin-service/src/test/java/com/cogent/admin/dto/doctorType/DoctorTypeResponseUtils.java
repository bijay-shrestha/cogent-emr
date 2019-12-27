package com.cogent.admin.dto.doctorType;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.DoctorType;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 10/11/2019
 */
public class DoctorTypeResponseUtils {
    public static List<DropDownResponseDTO> fetchActiveDoctorTypes() {
        return Arrays.asList(
                DropDownResponseDTO.builder()
                        .value(1L)
                        .label("Senior Doctor")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("Junior Doctor")
                        .build()
        );
    }

    public static DoctorType fetchDoctorType() {
        return new DoctorType(1L, "Senior Doctor", 'Y');
    }
}


