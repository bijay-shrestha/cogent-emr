package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-10-10
 */
@Repository
@Qualifier("appointmentModeRepositoryCustom")
public interface AppointmentModeRepositoryCustom {
    Long fetchAppointmentModeByName(String name);

    Long fetchAppointmentModeByIdAndName(Long id, String name);

    List<AppointmentModeMinimalResponseDTO> search(AppointmentModeSearchRequestDTO searchRequestDTO,
                                                   Pageable pageable);

    List<DropDownResponseDTO> fetchActiveAppointmentModeForDropdown();

    AppointmentModeResponseDTO fetchDetailsById(Long id);
}
