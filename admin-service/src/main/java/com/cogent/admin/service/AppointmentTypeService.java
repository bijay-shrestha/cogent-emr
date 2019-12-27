package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import com.cogent.persistence.model.AppointmentType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 2019-09-26
 */
public interface AppointmentTypeService {
    void save(AppointmentTypeRequestDTO requestDTO);

    void update(AppointmentTypeUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<AppointmentTypeMinimalResponseDTO> search(AppointmentTypeSearchRequestDTO searchRequestDTO,
                                                   Pageable pageable);

    List<DropDownResponseDTO> fetchAppointmentTypeForDropdown();

    AppointmentTypeResponseDTO fetchDetailsById(Long id);

    AppointmentType fetchAppointmentTypeById(Long id);
}
