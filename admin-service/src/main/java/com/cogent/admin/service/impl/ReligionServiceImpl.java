package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionUpdateRequestDTO;
import com.cogent.admin.dto.response.religion.ReligionDropdownDTO;
import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ReligionRepository;
import com.cogent.admin.service.ReligionService;
import com.cogent.persistence.model.Religion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.ReligionServiceMessages.RELIGION_NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.ReligionServiceMessages.RELIGION_NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ReligionLog.RELIGION;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ReligionUtils.*;

@Slf4j
@Service
@Transactional
public class ReligionServiceImpl implements ReligionService {

    private final ReligionRepository religionRepository;

    public ReligionServiceImpl(ReligionRepository religionRepository) {
        this.religionRepository = religionRepository;
    }

    @Override
    public void save(ReligionRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, RELIGION);

        validateName(religionRepository.fetchReligionByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToReligion(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, RELIGION, getDifferenceBetweenTwoTime(startTime));

    }

    private void save(Religion religion) {
        religionRepository.save(religion);
    }

    private void validateName(Long religionCount, String name) {
        if (religionCount.intValue() > 0)
            throw new DataDuplicationException(
                    Religion.class, RELIGION_NAME_DUPLICATION_MESSAGE + name, RELIGION_NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    @Override
    public void update(ReligionUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, RELIGION);

        Religion religion = findById(requestDTO.getId());

        validateName(religionRepository.findReligionByIdAndName(requestDTO.getId(), requestDTO.getName()),
                requestDTO.getName());

        save(convertToUpdatedReligion(requestDTO, religion));

        log.info(UPDATING_PROCESS_COMPLETED, RELIGION, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, RELIGION);

        Religion religion = findById(deleteRequestDTO.getId());

        save(convertToDeletedReligion(religion, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, RELIGION, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<ReligionResponseDTO> search(ReligionSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, RELIGION);

        List<ReligionResponseDTO> responseDTOS = religionRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, RELIGION, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<ReligionDropdownDTO> fetchReligionForDropDown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, RELIGION);

        List<ReligionDropdownDTO> responseDTOS = religionRepository.fetchReligionForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, RELIGION, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Religion fetchReligion(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, RELIGION);

        Religion religion = religionRepository.findActiveReligionById(id)
                .orElseThrow(() -> new NoContentFoundException(Religion.class, "id", id.toString()));

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, RELIGION, getDifferenceBetweenTwoTime(startTime));

        return religion;
    }

    public Religion findById(Long religionId) {
        return religionRepository.findReligionById(religionId)
                .orElseThrow(() -> RELIGION_WITH_GIVEN_ID_NOT_FOUND.apply(religionId));
    }

    private Function<Long, NoContentFoundException> RELIGION_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Religion.class, "id", id.toString());
    };
}
