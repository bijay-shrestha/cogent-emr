package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;
import com.cogent.admin.dto.response.referrer.ReferrerDropdownDTO;
import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ReferrerRepository;
import com.cogent.admin.service.ReferrerService;
import com.cogent.persistence.model.Referrer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.ReferrerServiceMessages.REFERRER_NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.ReferrerServiceMessages.REFERRER_NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ReferrerLog.REFERRER;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ReferrerUtils.*;
import static com.cogent.admin.utils.ReferrerUtils.convertToDeletedReferrer;

/**
 * @author Rupak
 */
@Service
@Transactional
@Slf4j
public class ReferrerServiceImpl implements ReferrerService {

    private final ReferrerRepository referrerRepository;

    public ReferrerServiceImpl(ReferrerRepository referrerRepository) {
        this.referrerRepository = referrerRepository;
    }

    @Override
    public void save(ReferrerRequestDTO referrerRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, REFERRER);

        validateName(referrerRepository.fetchReferrerByName(referrerRequestDTO.getName()),
                referrerRequestDTO.getName());

        save(convertDTOToReferrer(referrerRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, REFERRER, getDifferenceBetweenTwoTime(startTime));


    }

    @Override
    public void update(ReferrerUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, REFERRER);

        Referrer referrer = findById(requestDTO.getId());

        System.out.println(referrer.getId());

        validateName(referrerRepository.findReferrerByIdAndName(requestDTO.getId(), requestDTO.getName()),
                requestDTO.getName());

        save(convertToUpdatedReferrer(requestDTO, referrer));

        log.info(UPDATING_PROCESS_COMPLETED, REFERRER, getDifferenceBetweenTwoTime(startTime));


    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, REFERRER);

        Referrer referrer = findById(deleteRequestDTO.getId());

        save(convertToDeletedReferrer(referrer, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, REFERRER, getDifferenceBetweenTwoTime(startTime));


    }

    @Override
    public List<ReferrerResponseDTO> search(ReferrerSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, REFERRER);

        List<ReferrerResponseDTO> responseDTOS = referrerRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, REFERRER, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<ReferrerDropdownDTO> fetchReferrerForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, REFERRER);

        List<ReferrerDropdownDTO> responseDTOS = referrerRepository.fetchReferrerForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, REFERRER, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Referrer fetchReferrer(Long id) {
        return null;
    }

    private void save(Referrer referrer) {
        referrerRepository.save(referrer);
    }

    private void validateName(Long referrerCount, String name) {
        if (referrerCount.intValue() > 0)
            throw new DataDuplicationException(
                    Referrer.class, REFERRER_NAME_DUPLICATION_MESSAGE + name, REFERRER_NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private Referrer findById(Long id) {
        return referrerRepository.findReferrerStatusById(id).orElseThrow(() -> REFERRER_NAME_WITH_GIVEN_ID_NOT_FOUND.apply(id));
    }

    private Function<Long, NoContentFoundException> REFERRER_NAME_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Referrer.class, "id", id.toString());
    };

}
