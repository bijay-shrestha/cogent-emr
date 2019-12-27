package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;
import com.cogent.admin.dto.response.surname.SurnameDropdownDTO;
import com.cogent.admin.dto.response.surname.SurnameMinimalResponseDTO;
import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.EthnicityRepository;
import com.cogent.admin.repository.SurnameRepository;
import com.cogent.admin.service.SurnameService;
import com.cogent.persistence.model.Ethnicity;
import com.cogent.persistence.model.Surname;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.SurnameLog.SURNAME;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.SurnameUtils.*;

/**
 * @author smriti on 2019-09-12
 */
@Service
@Transactional
@Slf4j
public class SurnameServiceImpl implements SurnameService {

    private final SurnameRepository surnameRepository;

    private final EthnicityRepository ethnicityRepository;

    public SurnameServiceImpl(SurnameRepository surnameRepository,
                              EthnicityRepository ethnicityRepository) {
        this.surnameRepository = surnameRepository;
        this.ethnicityRepository = ethnicityRepository;
    }

    @Override
    public void save(SurnameRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, SURNAME);

        validateName(surnameRepository.fetchSurnameByName(requestDTO.getName()),
                requestDTO.getName());

        Ethnicity ethnicity = fetchActiveEthnicityById(requestDTO.getEthnicityId());

        save(convertDTOToSurname(requestDTO, ethnicity));

        log.info(SAVING_PROCESS_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(SurnameUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, SURNAME);

        Surname surname = findById(requestDTO.getId());

        validateName(surnameRepository.findSurnameByIdAndName(requestDTO.getId(), requestDTO.getName()),
                requestDTO.getName());

        Ethnicity ethnicity = fetchActiveEthnicityById(requestDTO.getEthnicityId());

        save(convertToUpdatedSurname(requestDTO, surname, ethnicity));

        log.info(UPDATING_PROCESS_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, SURNAME);

        Surname surname = findById(deleteRequestDTO.getId());

        save(convertToDeletedSurname(surname, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<SurnameMinimalResponseDTO> search(SurnameSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, SURNAME);

        List<SurnameMinimalResponseDTO> responseDTOS = surnameRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<SurnameDropdownDTO> fetchActiveSurnameForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SURNAME);

        List<SurnameDropdownDTO> responseDTOS = surnameRepository.fetchActiveSurnameForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Surname fetchSurname(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, SURNAME);

        Surname surname = surnameRepository.fetchSurname(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));

        return surname;
    }

    @Override
    public SurnameResponseDTO fetchSurnameDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, SURNAME);

        SurnameResponseDTO responseDTO = surnameRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, SURNAME, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    private void validateName(Long surnameCount, String name) {
        if (surnameCount.intValue() > 0)
            throw new DataDuplicationException(
                    Surname.class, NAME_DUPLICATION_MESSAGE + name, NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void save(Surname surname) {
        surnameRepository.save(surname);
    }

    public Surname findById(Long surnameId) {
        return surnameRepository.findSurnameById(surnameId)
                .orElseThrow(() -> SURNAME_WITH_GIVEN_ID_NOT_FOUND.apply(surnameId));
    }

    private Function<Long, NoContentFoundException> SURNAME_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Surname.class, "id", id.toString());
    };

    private Ethnicity fetchActiveEthnicityById(Long ethnicityId) {
        return ethnicityRepository.fetchActiveEthnicityById(ethnicityId)
                .orElseThrow(() -> new NoContentFoundException(Ethnicity.class, "ethnicityId", ethnicityId.toString()));
    }
}
