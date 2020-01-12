package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;
import com.cogent.admin.dto.response.hospital.HospitalDropDownResponseDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.HospitalRepository;
import com.cogent.admin.service.HospitalService;
import com.cogent.persistence.model.Hospital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.HospitalServiceMessages.HOSPITAL_NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.HospitalServiceMessages.HOSPITAL_NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.HospitalLog.HOSPITAL;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.HospitalUtils.*;

/**
 * @author Rupak
 */
@Service
@Transactional
@Slf4j
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public void save(HospitalRequestDTO hospitalRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, HOSPITAL);

        validateName(hospitalRepository.fetchHospitalByName(hospitalRequestDTO.getName()),
                hospitalRequestDTO.getName());

        save(convertDTOToHospital(hospitalRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, HOSPITAL, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<HospitalResponseDTO> searchHospital(HospitalSearchRequestDTO hospitalSearchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, HOSPITAL);

        List<HospitalResponseDTO> responseDTOS = hospitalRepository.search(hospitalSearchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, HOSPITAL, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public void deleteHospital(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, HOSPITAL);

        Hospital hospital = findById(deleteRequestDTO.getId());

        save(convertToDeletedHospital(hospital, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, HOSPITAL, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateHospital(HospitalUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, HOSPITAL);

        Hospital hospital = findById(updateRequestDTO.getId());

        System.out.println(hospital.getId());

        validateName(hospitalRepository.findHospitalByIdAndName(updateRequestDTO.getId(), updateRequestDTO.getName()),
                updateRequestDTO.getName());

        save(convertToUpdatedHospital(updateRequestDTO, hospital));

        log.info(UPDATING_PROCESS_COMPLETED, HOSPITAL, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public Hospital fetchHospital(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_ENTITY_PROCESS_STARTED, HOSPITAL);

        Hospital hospital = hospitalRepository.findActiveHospitalById(id).orElseThrow(() ->
                new NoContentFoundException(Hospital.class));

        log.info(FETCHING_ENTITY_PROCESS_COMPLETED, HOSPITAL, getDifferenceBetweenTwoTime(startTime));

        return hospital;
    }

    @Override
    public List<HospitalDropDownResponseDTO> fetchHospitalForDropDown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, HOSPITAL);

        List<HospitalDropDownResponseDTO> responseDTOS = hospitalRepository.fetchHospitalForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, HOSPITAL, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    private void save(Hospital hospital) {
        hospitalRepository.save(hospital);
    }

    private void validateName(Long hospital, String name) {
        if (hospital.intValue() > 0)
            throw new DataDuplicationException(
                    Hospital.class, HOSPITAL_NAME_DUPLICATION_MESSAGE + name, HOSPITAL_NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private Hospital findById(Long id) {
        return hospitalRepository.findHospitalById(id).orElseThrow(() -> HOSPITAL_NAME_WITH_GIVEN_ID_NOT_FOUND.apply(id));
    }

    private Function<Long, NoContentFoundException> HOSPITAL_NAME_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Hospital.class, "id", id.toString());
    };

}
