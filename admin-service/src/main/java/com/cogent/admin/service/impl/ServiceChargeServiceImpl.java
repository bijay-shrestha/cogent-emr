package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.admin.dto.response.servicecharge.BillingModeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BillingModeRepository;
import com.cogent.admin.repository.ServiceChargeRepository;
import com.cogent.admin.repository.ServiceRepository;
import com.cogent.admin.service.ServiceChargeService;
import com.cogent.persistence.model.BillingMode;
import com.cogent.persistence.model.Service;
import com.cogent.persistence.model.ServiceCharge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.ServiceServiceMessages.*;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ServiceChargeLog.SERVICE_CHARGE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ServiceChargeUtils.*;

/**
 * @author Sauravi Thapa 11/18/19
 */

@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class ServiceChargeServiceImpl implements ServiceChargeService {

    private final ServiceChargeRepository repository;

    private final ServiceRepository serviceRepository;

    private final BillingModeRepository billingModeRepository;

    public ServiceChargeServiceImpl(ServiceChargeRepository repository,
                                    ServiceRepository serviceRepository,
                                    BillingModeRepository billingModeRepository) {
        this.repository = repository;
        this.serviceRepository = serviceRepository;
        this.billingModeRepository = billingModeRepository;
    }

    @Override
    public void createServiceCharge(ServiceChargeRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, SERVICE_CHARGE);

        checkDuplicity(requestDTO.getServiceId(),requestDTO.getBillingModeId());

        save(parseToServiceCharge(requestDTO,
                getActiveService(requestDTO.getServiceId()),
                getActiveBillingMode(requestDTO.getBillingModeId())));

        log.info(SAVING_PROCESS_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteServiceCharge(DeleteRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, SERVICE_CHARGE);

        deleteServiceCharge.apply(requestDTO, getServiceCharge(requestDTO.getId()));

        deleteChildByServiceId(requestDTO.getId());

        log.info(DELETING_PROCESS_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateServiceCharge(ServiceChargeUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, SERVICE_CHARGE);

        ServiceCharge serviceChargeToUpdate = getServiceCharge(requestDTO.getId());

        CheckIfServiceExists(requestDTO.getId(), requestDTO.getServiceId(),requestDTO.getBillingModeId());

        deleteChildByServiceId(requestDTO.getId());

        update(requestDTO,
                serviceChargeToUpdate,
                getActiveService(requestDTO.getServiceId()),
                getActiveBillingMode(requestDTO.getBillingModeId()));

        log.info(UPDATING_PROCESS_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<ServiceChargeMinimalResponseDTO> searchServiceCharge(ServiceChargeSearchRequestDTO requestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, SERVICE_CHARGE);

        List<ServiceChargeMinimalResponseDTO> departmentMinimalResponseDTO = repository.searchServiceCharge
                (requestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

        return departmentMinimalResponseDTO;
    }

    @Override
    public ServiceChargeResponseDTO fetchServiceChargeDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, SERVICE_CHARGE);

        ServiceChargeResponseDTO departmentResponseDTO = repository
                .fetchServiceChargeDetailById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

        return departmentResponseDTO;
    }

    @Override
    public List<ServiceChargeDropDownResponseDTO> fetchDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SERVICE_CHARGE);

        List<ServiceChargeDropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchDropDownList().orElseThrow(() ->
                        new NoContentFoundException(Service.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<ServiceChargeDropDownResponseDTO> fetchActiveDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SERVICE_CHARGE);

        List<ServiceChargeDropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchActiveDropDownList().orElseThrow(() ->
                        new NoContentFoundException(Service.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<BillingModeDropDownResponseDTO> fetchActiveDropDownListByBillingModeId(Long billingModeId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SERVICE_CHARGE);

        List<BillingModeDropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchActiveDropDownListByBillingModeId(billingModeId).orElseThrow(() ->
                        new NoContentFoundException(Service.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SERVICE_CHARGE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }


    public void checkDuplicity(Long serviceId,List<Long> billngModeIds){
        billngModeIds.forEach(billingModeId -> {
            if(repository.fetchServiceChargeCountByServiceIdAndBillingModeId(serviceId,billingModeId) != BigInteger.valueOf(0)){
                throw new DataDuplicationException(Service.class,
                        SERVICE_WITH_BILLING_MODE_DUPLICATION_DEBUG_MESSAGE,
                        SERVICEWITH_BILLING_MODE_DUPLICATION_MESSAGE);
            }
        });
    }

    public void CheckIfServiceExists(Long id, Long serviceId, List<Long> billingModeIds) {
        billingModeIds.forEach(billingModeId -> {
            if (repository.CheckIfServiceExists(id, serviceId,billingModeId) != BigInteger.valueOf(0))
                throw new DataDuplicationException(Service.class,
                        SERVICE_WITH_BILLING_MODE_DUPLICATION_DEBUG_MESSAGE,
                        SERVICEWITH_BILLING_MODE_DUPLICATION_MESSAGE);
        });
    }

    public Service getActiveService(Long id) {
        return serviceRepository.fetchActiveServiceById(id).orElseThrow(() ->
                new NoContentFoundException(Service.class, "id", id.toString()));
    }

    public ServiceCharge getServiceCharge(Long id) {
        return repository.fetchServiceChargeById(id).orElseThrow(() ->
                new NoContentFoundException(ServiceCharge.class, "id", id.toString()));
    }

    public List<BillingMode> getActiveBillingMode(List<Long> ids) {
        List<BillingMode> billingModes = new ArrayList<>();
        ids.forEach(id -> {
            BillingMode billingMode = billingModeRepository.fetchActiveBillingModeById(id);
            billingModes.add(billingMode);
        });
        return billingModes;
    }

    public void save(ServiceCharge serviceCharge) {
        repository.saveAndFlush(serviceCharge);
    }

    public void deleteChildByServiceId(Long id) {
        repository.deleteChildDataById(id);
    }
}
