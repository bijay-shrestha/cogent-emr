package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedRequestDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.admin.dto.response.bed.BedMinimalResponseDTO;
import com.cogent.admin.dto.response.bed.BedResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BedRepository;
import com.cogent.admin.service.BedService;
import com.cogent.persistence.model.Bed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.BedLog.BED;
import static com.cogent.admin.utils.BedUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;

/**
 * @author Sauravi Thapa 11/1/19
 */

@Slf4j
@Service
@Transactional
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;

    public BedServiceImpl(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    @Override
    public void createBed(BedRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, BED);

        validatetDuplicityByNameOrCode(bedRepository.findBedByNameOrCode
                        (requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), Bed.class);

        save(requestDTO);

        log.info(SAVING_PROCESS_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteBed(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, BED);

        deleteBed.apply(fetchBedById(deleteRequestDTO.getId()), deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateBed(BedUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, BED);

        Bed toBeUpdatedBed = fetchBedById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(bedRepository.checkBedNameAndCodeIfExist(
                updateRequestDTO.getId(), updateRequestDTO.getName(), updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), Bed.class);

        updateBed.apply(toBeUpdatedBed, updateRequestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<BedMinimalResponseDTO> searchBed(BedSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, BED);

        List<BedMinimalResponseDTO> minimalResponseDTOS = bedRepository.searchBed(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }


    @Override
    public BedResponseDTO fetchBedDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, BED);

        BedResponseDTO responseDTO = bedRepository.fetchBedDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, BED);


        List<DropDownResponseDTO> dropDownResponseDTOS = bedRepository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Bed.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, BED);

        List<DropDownResponseDTO> dropDownResponseDTOS = bedRepository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(Bed.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, BED, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    public void save(BedRequestDTO requestDTO) {
        bedRepository.save(parseToBed.apply(requestDTO));
    }

    public Bed fetchBedById(Long id) {
        return bedRepository.fetchBedById(id).orElseThrow(() ->
                new NoContentFoundException(Bed.class, "id", id.toString()));
    }


}
