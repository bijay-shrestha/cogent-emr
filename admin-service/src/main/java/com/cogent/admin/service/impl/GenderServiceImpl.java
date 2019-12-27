package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.GenderRepository;
import com.cogent.admin.service.GenderService;
import com.cogent.persistence.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.GenderLog.GENDER;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti on 08/11/2019
 */
@Service
@Transactional
@Slf4j
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveGender() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, GENDER);

        List<DropDownResponseDTO> responseDTOS = genderRepository.fetchActiveGender();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, GENDER, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Gender fetchGenderById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, GENDER);

        Gender gender = genderRepository.fetchActiveGenderById(id)
                .orElseThrow(() -> new NoContentFoundException(Gender.class, "id", id.toString()));

        log.info(FETCHING_PROCESS_COMPLETED, GENDER, getDifferenceBetweenTwoTime(startTime));

        return gender;
    }
}
