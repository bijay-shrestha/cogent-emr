package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryResponseDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryMinimalResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DoctorCategoryRepository;
import com.cogent.admin.service.DoctorCategoryService;
import com.cogent.persistence.model.DoctorCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.CommonLogConstant.DELETING_PROCESS_COMPLETED;
import static com.cogent.admin.log.constants.DoctorCatgeoryLog.DOCTOR_CATEGORY;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DoctorCategoryUtils.*;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;

/**
 * @author Sauravi Thapa 11/21/19
 */

@Service
@Slf4j
@Transactional
public class DoctorCategoryServiceImpl implements DoctorCategoryService {

    @Autowired
    private final DoctorCategoryRepository repository;

    public DoctorCategoryServiceImpl(DoctorCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createDoctorCategory(DoctorCategoryRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_CATEGORY);

        validatetDuplicityByNameOrCode(repository.fetchDoctorCategoryByNameOrCode(
                requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(),
                requestDTO.getCode(), DoctorCategory.class);

        save(convertToDoctorCategory(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteDoctorCategory(DeleteRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DOCTOR_CATEGORY);

        deleteDoctorCategory.apply(fetchDoctorCategoryById(requestDTO.getId()), requestDTO);

        log.info(DELETING_PROCESS_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateDoctorCategory(DoctorCategoryUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_CATEGORY);

        DoctorCategory doctorCategoryToBeUpdated = fetchDoctorCategoryById(requestDTO.getId());

        validatetDuplicityByNameOrCode(repository.checkIfDoctorCategoryNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(),
                requestDTO.getCode(), DoctorCategory.class);

        updateDoctorCategory.apply(doctorCategoryToBeUpdated,requestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<DoctorCategoryMinimalResponseDTO> searchDoctorCategory(DoctorCategorySearchRequestDTO requestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, DOCTOR_CATEGORY);

        List<DoctorCategoryMinimalResponseDTO> minimalResponseDTOS = repository
                .searchDoctorCategory(requestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }

    @Override
    public DoctorCategoryResponseDTO fetchDoctorCategoryDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DOCTOR_CATEGORY);

        DoctorCategoryResponseDTO responseDTO = repository.fetchDoctorCategoryDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<DropDownResponseDTO> doctorCategoryDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DOCTOR_CATEGORY);

        List<DropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchDropDownList().orElseThrow(() ->
                        new NoContentFoundException(DoctorCategory.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> activeDoctorCategoryDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DOCTOR_CATEGORY);

        List<DropDownResponseDTO> dropDownResponseDTOS = repository
                .fetchActiveDropDownList().orElseThrow(() ->
                        new NoContentFoundException(DoctorCategory.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DOCTOR_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }


    public void save(DoctorCategory doctorCategory) {
        repository.save(doctorCategory);
    }

    public DoctorCategory fetchDoctorCategoryById(Long id) {
        return repository.fetchDoctorCategoryById(id).orElseThrow(() ->
                new NoContentFoundException(DoctorCategory.class, "id", id.toString()));
    }
}
