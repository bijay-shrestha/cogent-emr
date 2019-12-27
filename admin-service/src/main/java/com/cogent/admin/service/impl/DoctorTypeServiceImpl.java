package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DoctorTypeRepository;
import com.cogent.admin.service.DoctorTypeService;
import com.cogent.persistence.model.DoctorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.DoctorTypeLog.DOCTOR_TYPE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti on 08/11/2019
 */
@Service
@Transactional
@Slf4j
public class DoctorTypeServiceImpl implements DoctorTypeService {

    private final DoctorTypeRepository DoctorTypeRepository;

    public DoctorTypeServiceImpl(DoctorTypeRepository DoctorTypeRepository) {
        this.DoctorTypeRepository = DoctorTypeRepository;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDoctorType() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DOCTOR_TYPE);

        List<DropDownResponseDTO> responseDTOS = DoctorTypeRepository.fetchActiveDoctorType();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DOCTOR_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public DoctorType fetchDoctorTypeById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, DOCTOR_TYPE);

        DoctorType DoctorType = DoctorTypeRepository.fetchActiveDoctorTypeById(id)
                .orElseThrow(() -> new NoContentFoundException(DoctorType.class, "id", id.toString()));

        log.info(FETCHING_PROCESS_COMPLETED, DOCTOR_TYPE, getDifferenceBetweenTwoTime(startTime));

        return DoctorType;
    }
}
