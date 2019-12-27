package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BillTypeRepository;
import com.cogent.admin.service.BillTypeService;
import com.cogent.persistence.model.BillType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.BillTypeLog.BILL_TYPE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti on 2019-10-22
 */
@Service
@Transactional
@Slf4j
public class BillTypeServiceImpl implements BillTypeService {

    private final BillTypeRepository billTypeRepository;

    public BillTypeServiceImpl(BillTypeRepository billTypeRepository) {
        this.billTypeRepository = billTypeRepository;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveBillType() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, BILL_TYPE);

        List<DropDownResponseDTO> responseDTOS = billTypeRepository.fetchActiveBillTypes();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, BILL_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public BillType fetchBillTypeById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, BILL_TYPE);

        BillType billType = billTypeRepository.fetchActiveBillTypeById(id)
                .orElseThrow(() -> new NoContentFoundException(BillType.class));

        log.info(FETCHING_PROCESS_COMPLETED, BILL_TYPE, getDifferenceBetweenTwoTime(startTime));

        return billType;
    }
}
