package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
import com.cogent.admin.repository.ServiceRepository;
import com.cogent.admin.repository.ServiceTypeRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.ServiceService;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.Service;
import com.cogent.persistence.model.ServiceType;
import com.cogent.persistence.model.SubDepartment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ServiceLog.SERVICE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.ServiceUtils.*;

/**
 * @author Sauravi Thapa 11/18/19
 */

@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;

    private final DepartmentRepository departmentRepository;

    private final SubDepartmentRepository subDepartmentRepository;

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceServiceImpl(ServiceRepository repository,
                              DepartmentRepository departmentRepository,
                              SubDepartmentRepository subDepartmentRepository,
                              ServiceTypeRepository serviceTypeRepository) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.subDepartmentRepository = subDepartmentRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public void createService(ServiceRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, SERVICE);

        validatetDuplicityByNameOrCode(repository.fetchServiceByNameOrCode(requestDTO.getName(),
                requestDTO.getCode()), requestDTO.getName(),
                requestDTO.getCode(), Service.class);

        save(parseToService(requestDTO,
                getActiveDepartmentById(requestDTO.getDepartmentId()),
                !Objects.isNull(requestDTO.getSubDepartmentId()) ?
                        getActiveSubDepartmentById(requestDTO.getSubDepartmentId()) : null,
                getActiveServiceTypeById(requestDTO.getServiceTypeId())));

        log.info(SAVING_PROCESS_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void deleteService(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, SERVICE);

        deleteService.apply(getServiceById(deleteRequestDTO.getId()), deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateService(ServiceUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, SERVICE);

        Service serviceToUpdate = getServiceById(requestDTO.getId());

        validatetDuplicityByNameOrCode(repository.checkIfServiceNameAndCodeExists(
                requestDTO.getId(), requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), Service.class);

        update(requestDTO,
                serviceToUpdate,
                getActiveDepartmentById(requestDTO.getDepartmentId()),
                !Objects.isNull(requestDTO.getSubDepartmentId()) ?
                        getActiveSubDepartmentById(requestDTO.getSubDepartmentId()) : null,
                getActiveServiceTypeById(requestDTO.getServiceTypeId()));

        log.info(UPDATING_PROCESS_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<ServiceMinimalResponseDTO> searchService(ServiceSearchRequestDTO searchRequestDTO,
                                                         Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, SERVICE);

        List<ServiceMinimalResponseDTO> departmentMinimalResponseDTO = repository.searchService
                (searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));

        return departmentMinimalResponseDTO;
    }

    @Override
    public ServiceResponseDTO fetchServiceDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, SERVICE);

        ServiceResponseDTO departmentResponseDTO = repository
                .fetchServiceDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));

        return departmentResponseDTO;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList(Long departmentId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SERVICE);

        List<DropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchDropDownList(departmentId).orElseThrow(() ->
                        new NoContentFoundException(Service.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList(Long departmentId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SERVICE);

        List<DropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchActiveDropDownList(departmentId).orElseThrow(() ->
                        new NoContentFoundException(Service.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SERVICE, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }


    public ServiceType getActiveServiceTypeById(Long id) {
        return serviceTypeRepository.fetchActiveServiceTypeById(id).orElseThrow(() ->
                new NoContentFoundException(ServiceType.class, "id", id.toString()));
    }

    public Department getActiveDepartmentById(Long id) {
        return departmentRepository.findActiveDepartmentById(id).orElseThrow(() ->
                new NoContentFoundException(Department.class, "id", id.toString()));
    }

    public SubDepartment getActiveSubDepartmentById(Long id) {
        return subDepartmentRepository.findActiveSubDepartmentById(id).orElseThrow(() ->
                new NoContentFoundException(SubDepartment.class, "id", id.toString()));
    }

    public Service getServiceById(Long id) {
        return repository.fetchServiceById(id).orElseThrow(() ->
                new NoContentFoundException(Service.class, "id", id.toString()));
    }

    public void save(Service service) {
        repository.save(service);
    }
}
