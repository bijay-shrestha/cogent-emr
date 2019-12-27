package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AppointmentTypeRepository;
import com.cogent.admin.service.AppointmentTypeService;
import com.cogent.persistence.model.AppointmentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.AppointmentTypeLog.APPOINTMENT_TYPE;
import static com.cogent.admin.utils.AppointmentTypeUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti on 2019-09-26
 */
@Service
@Transactional
@Slf4j
public class AppointmentTypeServiceImpl implements AppointmentTypeService {
    private final AppointmentTypeRepository appointmentTypeRepository;

    public AppointmentTypeServiceImpl(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public void save(AppointmentTypeRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, APPOINTMENT_TYPE);

        validateName(appointmentTypeRepository.fetchAppointmentTypeByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToAppointmentType(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(AppointmentTypeUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, APPOINTMENT_TYPE);

        AppointmentType appointmentType = findById(requestDTO.getId());

        validateName(appointmentTypeRepository.fetchAppointmentTypeByIdAndName
                (requestDTO.getId(), requestDTO.getName()), requestDTO.getName());

        convertToUpdatedAppointmentType(requestDTO, appointmentType);

        log.info(UPDATING_PROCESS_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, APPOINTMENT_TYPE);

        AppointmentType appointmentType = findById(deleteRequestDTO.getId());

        convertToDeletedAppointmentType(appointmentType, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<AppointmentTypeMinimalResponseDTO> search(
            AppointmentTypeSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, APPOINTMENT_TYPE);

        List<AppointmentTypeMinimalResponseDTO> responseDTOS =
                appointmentTypeRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchAppointmentTypeForDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, APPOINTMENT_TYPE);

        List<DropDownResponseDTO> responseDTOS = appointmentTypeRepository.fetchAppointmentTypeForDropdown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public AppointmentTypeResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, APPOINTMENT_TYPE);

        AppointmentTypeResponseDTO responseDTO = appointmentTypeRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public AppointmentType fetchAppointmentTypeById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, APPOINTMENT_TYPE);

        AppointmentType appointmentType = appointmentTypeRepository.fetchActiveAppointmentTypeById(id)
                .orElseThrow(() -> new NoContentFoundException(AppointmentType.class));

        log.info(FETCHING_PROCESS_COMPLETED, APPOINTMENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return appointmentType;
    }

    private void validateName(Long appointmentTypeCount, String name) {
        if (appointmentTypeCount.intValue() > 0)
            throw new DataDuplicationException(AppointmentType.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void save(AppointmentType appointmentType) {
        appointmentTypeRepository.save(appointmentType);
    }

    public AppointmentType findById(Long appointmentTypeId) {
        return appointmentTypeRepository.findAppointmentTypeById(appointmentTypeId)
                .orElseThrow(() -> APPOINTMENT_TYPE_WITH_GIVEN_ID_NOT_FOUND.apply(appointmentTypeId));
    }

    private Function<Long, NoContentFoundException> APPOINTMENT_TYPE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AppointmentType.class, "id", id.toString());
    };
}
