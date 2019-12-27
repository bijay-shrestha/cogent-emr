package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.PaymentTypeRepository;
import com.cogent.admin.service.PaymentTypeService;
import com.cogent.persistence.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED;
import static com.cogent.admin.log.CommonLogConstant.FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN;
import static com.cogent.admin.log.constants.PaymentTypeLog.PAYMENT_TYPE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Service
@Slf4j
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository repository;

    public PaymentTypeServiceImpl(PaymentTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, PAYMENT_TYPE);


        List<DropDownResponseDTO> dropDownResponseDTOS = repository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Unit.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, PAYMENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, PAYMENT_TYPE);


        List<DropDownResponseDTO> dropDownResponseDTOS = repository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(Unit.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, PAYMENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }
}
