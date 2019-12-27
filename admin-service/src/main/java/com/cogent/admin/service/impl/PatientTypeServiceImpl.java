package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.PatientTypeRepository;
import com.cogent.admin.service.PatientTypeService;
import com.cogent.persistence.model.PatientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.PatientTypeLog.PATIENT_TYPE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.PatientTypeUtils.*;

/**
 * @author smriti on 2019-09-26
 */
@Service
@Transactional
@Slf4j
public class PatientTypeServiceImpl implements PatientTypeService {
    private final PatientTypeRepository patientTypeRepository;

    public PatientTypeServiceImpl(PatientTypeRepository patientTypeRepository) {
        this.patientTypeRepository = patientTypeRepository;
    }

    @Override
    public void save(PatientTypeRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, PATIENT_TYPE);

        validateName(patientTypeRepository.fetchPatientTypeByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToPatientType(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(PatientTypeUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, PATIENT_TYPE);

        PatientType patientType = findById(requestDTO.getId());

        validateName(patientTypeRepository.fetchPatientTypeByIdAndName
                (requestDTO.getId(), requestDTO.getName()), requestDTO.getName());

        convertToUpdatedPatientType(requestDTO, patientType);

        log.info(UPDATING_PROCESS_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, PATIENT_TYPE);

        PatientType patientType = findById(deleteRequestDTO.getId());

        convertToDeletedPatientType(patientType, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<PatientTypeMinimalResponseDTO> search(
            PatientTypeSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, PATIENT_TYPE);

        List<PatientTypeMinimalResponseDTO> responseDTOS =
                patientTypeRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchActivePatientTypeForDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, PATIENT_TYPE);

        List<DropDownResponseDTO> responseDTOS = patientTypeRepository.fetchActivePatientTypeForDropdown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public PatientTypeResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, PATIENT_TYPE);

        PatientTypeResponseDTO responseDTO = patientTypeRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public PatientType fetchPatientTypeById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, PATIENT_TYPE);

        PatientType patientType = patientTypeRepository.findActivePatientTypeById(id)
                .orElseThrow(() -> new NoContentFoundException(PatientType.class));

        log.info(FETCHING_PROCESS_COMPLETED, PATIENT_TYPE, getDifferenceBetweenTwoTime(startTime));

        return patientType;
    }

    private void validateName(Long PatientTypeCount, String name) {
        if (PatientTypeCount.intValue() > 0)
            throw new DataDuplicationException(PatientType.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void save(PatientType patientType) {
        patientTypeRepository.save(patientType);
    }

    public PatientType findById(Long PatientTypeId) {
        return patientTypeRepository.findPatientTypeById(PatientTypeId)
                .orElseThrow(() -> PATIENT_TYPE_WITH_GIVEN_ID_NOT_FOUND.apply(PatientTypeId));
    }

    private Function<Long, NoContentFoundException> PATIENT_TYPE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(PatientType.class, "id", id.toString());
    };
}
