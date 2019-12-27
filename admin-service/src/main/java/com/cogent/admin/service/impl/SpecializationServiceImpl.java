package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;
import com.cogent.admin.dto.response.specialization.SpecializationMinimalResponseDTO;
import com.cogent.admin.dto.response.specialization.SpecializationResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.SpecializationRepository;
import com.cogent.admin.service.SpecializationService;
import com.cogent.persistence.model.Specialization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.SpecializationLog.SPECIALIZATION;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.SpecializationUtils.*;

/**
 * @author smriti on 2019-08-11
 */
@Service
@Transactional
@Slf4j
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    @Override
    public void save(SpecializationRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, SPECIALIZATION);

        validateName(specializationRepository.fetchSpecializationByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToSpecialization(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(SpecializationUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, SPECIALIZATION);

        Specialization specialization = findById(requestDTO.getId());

        validateName(specializationRepository.fetchSpecializationByIdAndName
                (requestDTO.getId(), requestDTO.getName()), requestDTO.getName());

        save(convertToUpdatedSpecialization(requestDTO, specialization));

        log.info(UPDATING_PROCESS_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, SPECIALIZATION);

        Specialization specialization = findById(deleteRequestDTO.getId());

        save(convertToDeletedSpecialization(specialization, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<SpecializationMinimalResponseDTO> search(SpecializationSearchRequestDTO searchRequestDTO,
                                                         Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, SPECIALIZATION);

        List<SpecializationMinimalResponseDTO> responseDTOS =
                specializationRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveSpecializationForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SPECIALIZATION);

        List<DropDownResponseDTO> responseDTOS = specializationRepository.fetchActiveSpecializationForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public SpecializationResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, SPECIALIZATION);

        SpecializationResponseDTO responseDTO = specializationRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<DropDownResponseDTO> fetchSpecializationByDoctorId(Long DoctorId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SPECIALIZATION);

        List<DropDownResponseDTO> responseDTOS =
                specializationRepository.fetchSpecializationByDoctorId(DoctorId);

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Specialization fetchActiveSpecializationById(Long specializationId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, SPECIALIZATION);

        Specialization specialization = specializationRepository.findActiveSpecializationById(specializationId)
                .orElseThrow(() -> new NoContentFoundException(
                        Specialization.class, "specializationId", specializationId.toString()));

        log.info(FETCHING_PROCESS_COMPLETED, SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));

        return specialization;
    }

    private void validateName(Long specializationCount, String name) {
        if (specializationCount.intValue() > 0)
            throw new DataDuplicationException(Specialization.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void save(Specialization specialization) {
        specializationRepository.save(specialization);
    }

    public Specialization findById(Long specializationId) {
        return specializationRepository.findSpecializationById(specializationId)
                .orElseThrow(() -> SPECIALIZATION_WITH_GIVEN_ID_NOT_FOUND.apply(specializationId));
    }

    private Function<Long, NoContentFoundException> SPECIALIZATION_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Specialization.class, "id", id.toString());
    };
}
