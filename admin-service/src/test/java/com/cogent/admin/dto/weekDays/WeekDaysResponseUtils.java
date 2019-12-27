package com.cogent.admin.dto.weekDays;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.WeekDays;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 25/11/2019
 */
public class WeekDaysResponseUtils {

    public static List<DropDownResponseDTO> fetchActiveWeekDays() {
        return Arrays.asList(
                DropDownResponseDTO.builder()
                        .value(1L)
                        .label("SUNDAY")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("MONDAY")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("TUESDAY")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("WEDNESDAY")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("THURSDAY")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("FRIDAY")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("SATURDAY")
                        .build()
        );
    }

    public static WeekDays fetchWeekDays() {
        return new WeekDays(1L, "SUNDAY", "SUN", 'Y');
    }
}
