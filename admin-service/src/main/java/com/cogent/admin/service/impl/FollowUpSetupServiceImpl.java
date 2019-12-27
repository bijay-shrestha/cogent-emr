package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupMinimalResponseDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.FollowUpSetupRepository;
import com.cogent.admin.service.FollowUpSetupService;
import com.cogent.admin.service.PatientTypeService;
import com.cogent.persistence.model.FollowUpSetup;
import com.cogent.persistence.model.PatientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.FollowUpSetupLog.FOLLOW_UP_SETUP;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.FollowUpSetupUtils.*;

/**
 * @author smriti on 2019-11-04
 */
@Service
@Transactional
@Slf4j
public class FollowUpSetupServiceImpl implements FollowUpSetupService {

    private final PatientTypeService patientTypeService;

    private final FollowUpSetupRepository followUpSetupRepository;

    public FollowUpSetupServiceImpl(PatientTypeService patientTypeService,
                                    FollowUpSetupRepository followUpSetupRepository) {
        this.patientTypeService = patientTypeService;
        this.followUpSetupRepository = followUpSetupRepository;
    }

    @Override
    public void save(FollowUpSetupRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, FOLLOW_UP_SETUP);

        PatientType patientType = fetchPatientTypeById(requestDTO.getPatientTypeId());

        save(convertDTOToFollowUpSetup(requestDTO, patientType));

        log.info(SAVING_PROCESS_COMPLETED, FOLLOW_UP_SETUP, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(FollowUpSetupUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, FOLLOW_UP_SETUP);

        FollowUpSetup followUpSetup = findFollowUpSetupById(requestDTO.getId());

        PatientType patientType = fetchPatientTypeById(requestDTO.getPatientTypeId());

        convertDTOToUpdatedFollowUpSetup(requestDTO, patientType, followUpSetup);

        log.info(UPDATING_PROCESS_COMPLETED, FOLLOW_UP_SETUP, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, FOLLOW_UP_SETUP);

        FollowUpSetup followUpSetup = findFollowUpSetupById(deleteRequestDTO.getId());

        convertToDeletedFollowUpSetup(followUpSetup, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, FOLLOW_UP_SETUP, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<FollowUpSetupMinimalResponseDTO> fetchFollowUpSetup() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, FOLLOW_UP_SETUP);

        List<FollowUpSetupMinimalResponseDTO> responseDTOS = followUpSetupRepository.fetchFollowUpSetup();

        log.info(SEARCHING_PROCESS_COMPLETED, FOLLOW_UP_SETUP, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public FollowUpSetupResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, FOLLOW_UP_SETUP);

        FollowUpSetupResponseDTO responseDTO = followUpSetupRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, FOLLOW_UP_SETUP, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    private FollowUpSetup findFollowUpSetupById(Long followUpSetupId) {
        return followUpSetupRepository.findFollowUpSetupById(followUpSetupId)
                .orElseThrow(() -> FOLLOW_UP_SETUP_WITH_GIVEN_ID_NOT_FOUND.apply(followUpSetupId));
    }

    private PatientType fetchPatientTypeById(Long patientTypeId) {
        return patientTypeService.fetchPatientTypeById(patientTypeId);
    }

    private void save(FollowUpSetup followUpSetup) {
        followUpSetupRepository.save(followUpSetup);
    }

    private Function<Long, NoContentFoundException> FOLLOW_UP_SETUP_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(FollowUpSetup.class, "id", id.toString());
    };
}
