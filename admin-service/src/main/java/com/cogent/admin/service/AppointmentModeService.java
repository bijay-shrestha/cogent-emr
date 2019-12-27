package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import com.cogent.persistence.model.AppointmentMode;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 2019-10-10
 */
public interface AppointmentModeService {
    void save(AppointmentModeRequestDTO requestDTO);

    void update(AppointmentModeUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<AppointmentModeMinimalResponseDTO> search(AppointmentModeSearchRequestDTO searchRequestDTO,
                                                   Pageable pageable);

    List<DropDownResponseDTO> fetchAppointmentModeForDropdown();

    AppointmentModeResponseDTO fetchDetailsById(Long id);

    AppointmentMode fetchAppointmentModeById(Long id);
}
