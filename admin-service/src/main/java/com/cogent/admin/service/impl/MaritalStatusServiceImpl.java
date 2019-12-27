package com.cogent.admin.service.impl;

import com.cogent.admin.constants.ErrorMessageConstants;
import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusDropdownDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.MaritalStatusRepository;
import com.cogent.admin.service.MaritalStatusService;
import com.cogent.persistence.model.MaritalStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.MaritalStatusServiceMessages.MARITAL_STATUS_NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.MaritalStatusLog.MARITAL_STATUS;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.MaritalStatusUtils.*;

@Service
@Transactional
@Slf4j
public class MaritalStatusServiceImpl implements MaritalStatusService {
    private final MaritalStatusRepository maritalStatusRepository;

    public MaritalStatusServiceImpl(MaritalStatusRepository maritalStatusRepository) {
        this.maritalStatusRepository = maritalStatusRepository;
    }

    @Override
    public void save(MaritalStatusRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, MARITAL_STATUS);

        validateName(maritalStatusRepository.fetchMaritalStatusByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToMaritalStatus(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, MARITAL_STATUS, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(MaritalStatusUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, MARITAL_STATUS);

        MaritalStatus maritalStatus = findById(requestDTO.getId());

        System.out.println(maritalStatus.getId());

        validateName(maritalStatusRepository.findMaritalStatusByIdAndName(requestDTO.getId(), requestDTO.getName()),
                requestDTO.getName());

        save(convertToUpdatedMaritalStatus(requestDTO, maritalStatus));

        log.info(UPDATING_PROCESS_COMPLETED, MARITAL_STATUS, getDifferenceBetweenTwoTime(startTime));

    }


    private MaritalStatus findById(Long id) {
        return maritalStatusRepository.findMaritalStatusById(id).orElseThrow(() -> MARITAL_STATUS_NAME_WITH_GIVEN_ID_NOT_FOUND.apply(id));
    }

    private Function<Long, NoContentFoundException> MARITAL_STATUS_NAME_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(MaritalStatus.class, "id", id.toString());
    };

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, MARITAL_STATUS);

        MaritalStatus maritalStatus = findById(deleteRequestDTO.getId());

        save(convertToDeletedMaritalStatus(maritalStatus, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, MARITAL_STATUS, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<MaritalStatusResponseDTO> search(MaritalStatusSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, MARITAL_STATUS);

        List<MaritalStatusResponseDTO> responseDTOS = maritalStatusRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, MARITAL_STATUS, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<MaritalStatusDropdownDTO> fetchMaritalStatusForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, MARITAL_STATUS);

        List<MaritalStatusDropdownDTO> responseDTOS = maritalStatusRepository.fetchMaritalStatusForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, MARITAL_STATUS, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public MaritalStatus fetchMaritalStatus(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, MARITAL_STATUS);

        MaritalStatus maritalStatus = maritalStatusRepository.findActiveMaritalStatusById(id)
                .orElseThrow(() -> new NoContentFoundException(MaritalStatus.class, "id", id.toString()));

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, MARITAL_STATUS, getDifferenceBetweenTwoTime(startTime));

        return maritalStatus;
    }

    private void save(MaritalStatus maritalStatus) {
        maritalStatusRepository.save(maritalStatus);
    }

    private void validateName(Long maritalStatus, String name) {
        if (maritalStatus.intValue() > 0)
            throw new DataDuplicationException(
                    MaritalStatus.class, ErrorMessageConstants.MaritalStatusServiceMessages.MARITAL_STATUS_NAME_DUPLICATION_MESSAGE + name, MARITAL_STATUS_NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }


}
