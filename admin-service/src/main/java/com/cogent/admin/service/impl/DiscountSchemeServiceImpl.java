package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeUpdateRequestDTO;
import com.cogent.admin.dto.discountscheme.discountschemedetails.DepartmentDiscountRequestDTO;
import com.cogent.admin.dto.discountscheme.discountschemedetails.ServiceDiscountRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.*;
import com.cogent.admin.service.DiscountSchemeService;
import com.cogent.persistence.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.DiscountSchemeLog.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DiscountSchemeUtils.*;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static java.lang.Boolean.FALSE;
import static com.cogent.admin.constants.StringConstant.D;

/**
 * @author Sauravi Thapa 11/11/19
 */

@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class DiscountSchemeServiceImpl implements DiscountSchemeService {

    private final DiscountSchemeRepository repository;

    private final DepartmentRepository departmentRepository;

    private final ServiceRepository serviceRepository;

    private final ServiceDiscountSchemeRepository serviceDiscountSchemeRepository;

    private final DepartmentDiscountSchemeRepository departmentDiscountSchemeRepository;


    public DiscountSchemeServiceImpl(DiscountSchemeRepository repository,
                                     DepartmentRepository departmentRepository,
                                     ServiceRepository serviceRepository,
                                     ServiceDiscountSchemeRepository serviceDiscountSchemeRepository,
                                     DepartmentDiscountSchemeRepository departmentDiscountSchemeRepository) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.serviceRepository = serviceRepository;
        this.serviceDiscountSchemeRepository = serviceDiscountSchemeRepository;
        this.departmentDiscountSchemeRepository = departmentDiscountSchemeRepository;
    }

    @Override
    public void createDiscountScheme(DiscountSchemeRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DISCOUNT_SCHEME);

        validatetDuplicityByNameOrCode(repository.fetchDiscountSchemeByNameOrCode(requestDTO.getName(),
                requestDTO.getCode()), requestDTO.getName(),
                requestDTO.getCode(), Department.class);

        DiscountScheme discountScheme = saveDiscountScheme(parseToDiscountScheme.apply(requestDTO));

        if (discountScheme.getHas_netDiscount().equals(FALSE)) {
            saveDiscountSchemeDetails(requestDTO, discountScheme);
        }

        log.info(SAVING_PROCESS_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void deleteDiscountScheme(DeleteRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DISCOUNT_SCHEME);

        DiscountScheme discountScheme = getDiscountSchemeById(requestDTO.getId());

        deleteDiscountScheme.apply(discountScheme, requestDTO);

        if (!discountScheme.getHas_netDiscount()) {
            deleteDepartmentDiscountList(getDepartmentDiscountListByDiscountSchemeId(requestDTO.getId()), requestDTO);
            deleteServiceDiscountList(getserviceDiscountListByDiscountSchemeId(requestDTO.getId()), requestDTO);
        }

        log.info(DELETING_PROCESS_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateDiscountScheme(DiscountSchemeUpdateRequestDTO updateRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DISCOUNT_SCHEME);

        DiscountScheme discountSchemeToUpdate = getDiscountSchemeById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(repository.checkIfDiscountSchemeNameAndCodeExists(
                updateRequestDTO.getId(), updateRequestDTO.getName(), updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), DiscountScheme.class);

        if (!discountSchemeToUpdate.getHas_netDiscount() && updateRequestDTO.getHas_netDiscount()) {
            updateWithNetDiscount(updateRequestDTO);
        } else {
            updateWithoutNetDiscount(updateRequestDTO, discountSchemeToUpdate);
        }

        update.apply(discountSchemeToUpdate, updateRequestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<DiscountSchemeMinimalResponseDTO> searchDiscountScheme(DiscountSchemeSearchRequestDTO searchRequestDTO,
                                                                       Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, DISCOUNT_SCHEME);

        List<DiscountSchemeMinimalResponseDTO> minimalResponseDTOS = repository.searchDiscountScheme
                (searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }

    @Override
    public DiscountSchemeResponseDTO fetchDiscountSchemeDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DISCOUNT_SCHEME);

        DiscountSchemeResponseDTO responseDTO = repository.fetchDiscountSchemeDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<DiscountDropDownResponseDTO> fetchActiveDropDownListWithNetDiscount() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISCOUNT_SCHEME);

        List<DiscountDropDownResponseDTO> dropDownResponseDTOS = repository.activeDropDownListWithNetDiscount()
                .orElseThrow(() -> new NoContentFoundException(DiscountScheme.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DiscountDropDownResponseDTO> fetchDropDownListWithNetDiscount() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISCOUNT_SCHEME);

        List<DiscountDropDownResponseDTO> dropDownResponseDTOS = repository.dropDownListWithNetDiscount()
                .orElseThrow(() -> new NoContentFoundException(DiscountScheme.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }


    @Override
    public List<DepartmentDiscountDropDownResponseDTO> fetchActiveDropDownListByDepartmentId(Long departmentId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISCOUNT_SCHEME);

        List<DepartmentDiscountDropDownResponseDTO> dropDownResponseDTOS = repository
                .activeDropDownListByDepartmentId(departmentId)
                .orElseThrow(() -> new NoContentFoundException(DiscountScheme.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DepartmentDiscountDropDownResponseDTO> fetchDropDownListByDepartmentId(Long departmentId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISCOUNT_SCHEME);

        List<DepartmentDiscountDropDownResponseDTO> dropDownResponseDTOS = repository
                .dropDownListByDepartmentId(departmentId)
                .orElseThrow(() -> new NoContentFoundException(DiscountScheme.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<ServiceDiscountDropDownResponseDTO> fetchActiveDropDownListByServiceId(Long serviceId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISCOUNT_SCHEME);

        List<ServiceDiscountDropDownResponseDTO> dropDownResponseDTOS = repository
                .activeDropDownListByServiceId(serviceId)
                .orElseThrow(() -> new NoContentFoundException(DiscountScheme.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<ServiceDiscountDropDownResponseDTO> fetchDropDownListByServiceId(Long serviceId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISCOUNT_SCHEME);

        List<ServiceDiscountDropDownResponseDTO> dropDownResponseDTOS = repository
                .dropDownListByServiceId(serviceId)
                .orElseThrow(() -> new NoContentFoundException(DiscountScheme.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public void deleteDepartmentDiscount(DeleteRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DEPARTMENT_DISCOUNT_SCHEME);

        DepartmentDiscount departmentDiscount=getDepartmentDiscountById(requestDTO.getId());

        deleteDepartmentDiscount.apply(departmentDiscount, requestDTO);

        log.info(DELETING_PROCESS_COMPLETED, DEPARTMENT_DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void deleteServiceDiscount(DeleteRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, SERVICE_DISCOUNT_SCHEME);

        ServiceDiscount serviceDiscount = getServiceDiscountById(requestDTO.getId());

        deleteServiceDiscount.apply(serviceDiscount, requestDTO);

        log.info(DELETING_PROCESS_COMPLETED, SERVICE_DISCOUNT_SCHEME,
                getDifferenceBetweenTwoTime(startTime));

    }

    public void saveDiscountSchemeDetails(DiscountSchemeRequestDTO requestDTO, DiscountScheme discountScheme) {

        if (requestDTO.getDepartmentDiscountList().get(0).getDepartmentId() != null) {
            saveDepartmentDiscountDetails(requestDTO, discountScheme);
        }
        if (requestDTO.getServiceDiscountList().get(0).getServiceId() != null) {
            saveServiceDiscountDetails(requestDTO, discountScheme);
        }
    }

    public void saveServiceDiscountDetails(DiscountSchemeRequestDTO schemeRequestDTO,
                                           DiscountScheme discountScheme) {

        schemeRequestDTO.getServiceDiscountList().forEach(requestDTO -> {
            saveServiceDiscount(parseToServiceDiscountScheme(getServiceById(requestDTO.getServiceId()),
                            requestDTO.getServiceDiscount(), schemeRequestDTO.getStatus(), discountScheme));
        });
    }

    public void saveDepartmentDiscountDetails(DiscountSchemeRequestDTO schemeRequestDTO,
                                              DiscountScheme discountScheme) {

        schemeRequestDTO.getDepartmentDiscountList().forEach(requestDTO -> {
            saveDepartmentDiscount(parseToDepartmentDiscountScheme(getDepartmentById(requestDTO.getDepartmentId()),
                            requestDTO.getDepartmentDiscount(), schemeRequestDTO.getStatus(), discountScheme));
        });
    }

    public void deleteDepartmentDiscountList(List<DepartmentDiscount> departmentDiscountList,
                                             DeleteRequestDTO requestDTO) {
        departmentDiscountList.forEach(departmentDiscount ->
            deleteDepartmentDiscount.apply(departmentDiscount, requestDTO));
    }

    public void deleteServiceDiscountList(List<ServiceDiscount> serviceDiscountList,
                                          DeleteRequestDTO requestDTO) {
        serviceDiscountList.forEach(serviceDiscount ->
                deleteServiceDiscount.apply(serviceDiscount, requestDTO));
    }

    public void updateWithNetDiscount(DiscountSchemeUpdateRequestDTO updateRequestDTO) {

        getDepartmentDiscountListByDiscountSchemeId(updateRequestDTO.getId()).forEach(departmentDiscount -> {
            deleteDepartmentDiscount.apply(departmentDiscount, ConvertToDeleteRequestDTO
                    .apply(updateRequestDTO.getRemarks(),D));
        });

        getserviceDiscountListByDiscountSchemeId(updateRequestDTO.getId()).forEach(serviceDiscount -> {
            deleteServiceDiscount.apply(serviceDiscount, ConvertToDeleteRequestDTO
                    .apply(updateRequestDTO.getRemarks(), D));
        });
    }

    public void updateWithoutNetDiscount(DiscountSchemeUpdateRequestDTO discountSchemeUpdateRequestDTO,
                                         DiscountScheme discountSchemeToUpdate) {


        if (discountSchemeUpdateRequestDTO.getDepartmentDiscountList().get(0).getDepartmentId() != null) {
            discountSchemeUpdateRequestDTO.getDepartmentDiscountList().forEach(departmentDiscountRequestDTO -> {

                DepartmentDiscount departmentDiscount = getDepartmentDiscountByDepartmentAndDiscountSchemeId(
                        departmentDiscountRequestDTO.getDepartmentId(),
                        discountSchemeUpdateRequestDTO.getId());

                updateDepartmentDiscount(departmentDiscount,
                        departmentDiscountRequestDTO,
                        discountSchemeUpdateRequestDTO,
                        discountSchemeToUpdate);

            });
        }

        if (discountSchemeUpdateRequestDTO.getServiceDiscountList().get(0).getServiceId() != null) {
            discountSchemeUpdateRequestDTO.getServiceDiscountList().forEach(serviceDiscountRequestDTO -> {

                ServiceDiscount serviceDiscount = getServiceDiscountByDiscountSchemeAndServiceId(
                        discountSchemeUpdateRequestDTO.getId(),
                        serviceDiscountRequestDTO.getServiceId());

                updateServiceDiscount(serviceDiscount,
                        serviceDiscountRequestDTO,
                        discountSchemeUpdateRequestDTO,
                        discountSchemeToUpdate);

            });
        }
    }

    public void updateDepartmentDiscount(DepartmentDiscount departmentDiscount,
                                         DepartmentDiscountRequestDTO departmentDiscountRequestDTO,
                                         DiscountSchemeUpdateRequestDTO discountSchemeUpdateRequestDTO,
                                         DiscountScheme discountSchemeToUpdate) {
        if (departmentDiscount != null) {
            updateDepartmentDiscountDetails(departmentDiscount,
                    departmentDiscountRequestDTO.getDepartmentDiscount(),
                    discountSchemeUpdateRequestDTO);
        } else {
            saveDepartmentDiscount(parseToDepartmentDiscountScheme(
                    getDepartmentById(departmentDiscountRequestDTO.getDepartmentId()),
                    departmentDiscountRequestDTO.getDepartmentDiscount(),
                    discountSchemeUpdateRequestDTO.getStatus(),
                    discountSchemeToUpdate));
        }
    }

    public void updateServiceDiscount(ServiceDiscount serviceDiscount,
                                      ServiceDiscountRequestDTO serviceDiscountRequestDTO,
                                      DiscountSchemeUpdateRequestDTO discountSchemeUpdateRequestDTO,
                                      DiscountScheme discountSchemeToUpdate) {
        if (serviceDiscount != null) {
            updateServiceDiscountDetails(serviceDiscount,
                    serviceDiscountRequestDTO.getServiceDiscount(),
                    discountSchemeUpdateRequestDTO);
        } else {
            saveServiceDiscount(parseToServiceDiscountScheme(
                    getServiceById(serviceDiscountRequestDTO.getServiceId()),
                    serviceDiscountRequestDTO.getServiceDiscount(),
                    discountSchemeUpdateRequestDTO.getStatus(),
                    discountSchemeToUpdate));
        }
    }


    public DiscountScheme saveDiscountScheme(DiscountScheme discountScheme) {
        return repository.save(discountScheme);
    }

    public void saveDepartmentDiscount(DepartmentDiscount departmentDiscount) {
        departmentDiscountSchemeRepository.save(departmentDiscount);
    }

    public void saveServiceDiscount(ServiceDiscount serviceDiscount) {
        serviceDiscountSchemeRepository.save(serviceDiscount);
    }

    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findActiveDepartmentById(departmentId).orElseThrow(()
                -> new NoContentFoundException(Department.class, "id", departmentId.toString()));
    }

    public Service getServiceById(Long serviceId) {
        return serviceRepository.fetchActiveServiceById(serviceId).orElseThrow(()
                -> new NoContentFoundException(Service.class, "id", serviceId.toString()));

    }
    public DiscountScheme getDiscountSchemeById(Long id) {
        return repository.fetchDiscountSchemeById(id).orElseThrow(()
                -> new NoContentFoundException(DiscountScheme.class, "id", id.toString()));
    }

    public List<ServiceDiscount> getserviceDiscountListByDiscountSchemeId(Long discountSchemeId) {
        return serviceDiscountSchemeRepository.fetchByDiscountSchemeId(discountSchemeId);
    }

    public List<DepartmentDiscount> getDepartmentDiscountListByDiscountSchemeId(Long discountSchemeId) {
        return departmentDiscountSchemeRepository.fetchByDiscountSchemeId(discountSchemeId);
    }

    public DepartmentDiscount getDepartmentDiscountByDepartmentAndDiscountSchemeId(Long departmentId,
                                                                                   Long discountSchemeId) {
        return departmentDiscountSchemeRepository.fetchByDiscountSchemeAndDepartmentId(departmentId, discountSchemeId);
    }

    public ServiceDiscount getServiceDiscountByDiscountSchemeAndServiceId(Long discountSchemeId, Long serviceId) {
        return serviceDiscountSchemeRepository.fetchByDiscountSchemeAndServiceId(discountSchemeId, serviceId);
    }

    public DepartmentDiscount getDepartmentDiscountById(Long id){
        return departmentDiscountSchemeRepository.fetchById(id).orElseThrow(()
                -> new NoContentFoundException(DepartmentDiscount.class,"id",id.toString()));
    }

    public ServiceDiscount getServiceDiscountById(Long id){
        return serviceDiscountSchemeRepository.fetchById(id).orElseThrow(()
                -> new NoContentFoundException(ServiceDiscount.class,"id",id.toString()));
    }


}