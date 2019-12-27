package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-09-26
 */
@Repository
@Qualifier("appointmentTypeRepositoryCustom")
public interface AppointmentTypeRepositoryCustom {
    Long fetchAppointmentTypeByName(String name);

    Long fetchAppointmentTypeByIdAndName(Long id, String name);

    List<AppointmentTypeMinimalResponseDTO> search(AppointmentTypeSearchRequestDTO searchRequestDTO,
                                                   Pageable pageable);

    List<DropDownResponseDTO> fetchAppointmentTypeForDropdown();

    AppointmentTypeResponseDTO fetchDetailsById(Long id);
}
