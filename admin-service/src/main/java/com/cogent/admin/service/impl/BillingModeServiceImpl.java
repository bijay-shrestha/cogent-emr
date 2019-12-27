package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeUpdateRequestDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeMinimalResponseDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BillingModeRepository;
import com.cogent.admin.service.BillingModeService;
import com.cogent.persistence.model.BillingMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.BillingModeLog.BILLING_MODE;
import static com.cogent.admin.utils.BillingModeUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;

/**
 * @author Sauravi Thapa 12/4/19
 */

@Service
@Transactional
@Slf4j
public class BillingModeServiceImpl implements BillingModeService {

    private final BillingModeRepository repository;

    public BillingModeServiceImpl(BillingModeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createBillingMode(BillingModeRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, BILLING_MODE);

        validatetDuplicityByNameOrCode(repository.findBillingModeByNameOrCode
                        (requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), BillingMode.class);

        save(parseToBillingMode.apply(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteBillingMode(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, BILLING_MODE);

        deleteBillingMode.apply(fetchBillingModeById(deleteRequestDTO.getId()), deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateBillingMode(BillingModeUpdateRequestDTO updateRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, BILLING_MODE);

        BillingMode bllingModeToBeUpdated = fetchBillingModeById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(repository.checkIfBillingModeNameAndCodeExists(
                updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), BillingMode.class);

        updateBillingMode.apply(bllingModeToBeUpdated, updateRequestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<BillingModeMinimalResponseDTO> searchBillingMode(BillingModeSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, BILLING_MODE);

        List<BillingModeMinimalResponseDTO> minimalResponseDTOS = repository.searchBillingMode(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }

    @Override
    public BillingModeResponseDTO fetchBillingModeDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, BILLING_MODE);

        BillingModeResponseDTO responseDTO = repository.fetchBillingModeDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, BILLING_MODE);


        List<DropDownResponseDTO> dropDownResponseDTOS = repository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(BillingMode.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, BILLING_MODE);


        List<DropDownResponseDTO> dropDownResponseDTOS = repository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(BillingMode.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, BILLING_MODE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    public BillingMode fetchBillingModeById(Long id) {
        return repository.fetchBillingModeById(id).orElseThrow(() ->
                new NoContentFoundException(BillingMode.class, "id", id.toString()));
    }



    public void save(BillingMode billingMode) {
        repository.save(billingMode);
    }
}
