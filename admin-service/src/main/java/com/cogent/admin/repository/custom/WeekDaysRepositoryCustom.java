package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 25/11/2019
 */
@Repository
@Qualifier("weekDaysRepositoryCustom")
public interface WeekDaysRepositoryCustom {

    List<DropDownResponseDTO> fetchActiveWeekDays();
}
