package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.WeekDays;

import java.util.List;

/**
 * @author smriti on 25/11/2019
 */
public interface WeekDaysService {
    List<DropDownResponseDTO> fetchActiveWeekDays();

    WeekDays fetchWeekDaysById(Long id);
}
