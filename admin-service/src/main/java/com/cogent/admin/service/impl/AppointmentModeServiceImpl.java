package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AppointmentModeRepository;
import com.cogent.admin.service.AppointmentModeService;
import com.cogent.persistence.model.AppointmentMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.AppointmentModeLog.APPOINTMENT_MODE;
import static com.cogent.admin.utils.AppointmentModeUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti on 2019-10-10
 */
@Service
@Transactional
@Slf4j
public class AppointmentModeServiceImpl implements AppointmentModeService {
    private final AppointmentModeRepository appointmentModeRepository;

    public AppointmentModeServiceImpl(AppointmentModeRepository appointmentModeRepository) {
        this.appointmentModeRepository = appointmentModeRepository;
    }

    @Override
    public void save(AppointmentModeRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, APPOINTMENT_MODE);

        validateName(appointmentModeRepository.fetchAppointmentModeByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToAppointmentMode(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(AppointmentModeUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, APPOINTMENT_MODE);

        AppointmentMode appointmentMode = findById(requestDTO.getId());

        validateName(appointmentModeRepository.fetchAppointmentModeByIdAndName
                (requestDTO.getId(), requestDTO.getName()), requestDTO.getName());

        convertToUpdatedAppointmentMode(requestDTO, appointmentMode);

        log.info(UPDATING_PROCESS_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, APPOINTMENT_MODE);

        AppointmentMode appointmentMode = findById(deleteRequestDTO.getId());

        convertToDeletedAppointmentMode(appointmentMode, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<AppointmentModeMinimalResponseDTO> search(
            AppointmentModeSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, APPOINTMENT_MODE);

        List<AppointmentModeMinimalResponseDTO> responseDTOS =
                appointmentModeRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchAppointmentModeForDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, APPOINTMENT_MODE);

        List<DropDownResponseDTO> responseDTOS = appointmentModeRepository.fetchActiveAppointmentModeForDropdown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public AppointmentModeResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, APPOINTMENT_MODE);

        AppointmentModeResponseDTO responseDTO = appointmentModeRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public AppointmentMode fetchAppointmentModeById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, APPOINTMENT_MODE);

        AppointmentMode appointmentMode = appointmentModeRepository.fetchActiveAppointmentModeById(id)
                .orElseThrow(() -> new NoContentFoundException(AppointmentMode.class));

        log.info(FETCHING_PROCESS_COMPLETED, APPOINTMENT_MODE, getDifferenceBetweenTwoTime(startTime));

        return appointmentMode;
    }

    private void validateName(Long appointmentModeCount, String name) {
        if (appointmentModeCount.intValue() > 0)
            throw new DataDuplicationException(AppointmentMode.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void save(AppointmentMode appointmentMode) {
        appointmentModeRepository.save(appointmentMode);
    }

    public AppointmentMode findById(Long appointmentModeId) {
        return appointmentModeRepository.findAppointmentModeById(appointmentModeId)
                .orElseThrow(() -> APPOINTMENT_MODE_WITH_GIVEN_ID_NOT_FOUND.apply(appointmentModeId));
    }

    private Function<Long, NoContentFoundException> APPOINTMENT_MODE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AppointmentMode.class, "id", id.toString());
    };
}
