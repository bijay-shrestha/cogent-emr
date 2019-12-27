package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityUpdateRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityMinimalResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.EthnicityRepository;
import com.cogent.admin.service.EthnicityService;
import com.cogent.persistence.model.Ethnicity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.EthnicityLog.ETHNICITY;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.EthnicityUtils.convertToEthnicityInfo;
import static com.cogent.admin.utils.EthnicityUtils.updateEthnicity;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

@Slf4j
@Service
@Transactional
public class EthnicityServiceImpl implements EthnicityService {

    private final EthnicityRepository ethnicityRepository;

    public EthnicityServiceImpl(EthnicityRepository ethnicityRepository) {
        this.ethnicityRepository = ethnicityRepository;
    }

    @Override
    public void createEthnicity(EthnicityRequestDTO ethnicityRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, ETHNICITY);

        validatetDuplicityByNameOrCode(ethnicityRepository.fetchEthnicityByNameOrCode
                        (ethnicityRequestDTO.getName(), ethnicityRequestDTO.getCode()),
                ethnicityRequestDTO.getName(), ethnicityRequestDTO.getCode(), Ethnicity.class);

        saveEthnicity(convertToEthnicityInfo(ethnicityRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteEthnicity(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, ETHNICITY);

        saveEthnicity(updateEthnicity.apply(fetchEthnicityById(deleteRequestDTO.getId()), deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateEthnicity(EthnicityUpdateRequestDTO ethnicityUpdateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, ETHNICITY);

        Ethnicity toBeUpdatedEthnicity = fetchEthnicityById(ethnicityUpdateRequestDTO.getId());

        validatetDuplicityByNameOrCode(ethnicityRepository.checkEthnicityNameAndCodeIfExist(
                ethnicityUpdateRequestDTO.getId(), ethnicityUpdateRequestDTO.getName(),
                ethnicityUpdateRequestDTO.getCode()), ethnicityUpdateRequestDTO.getName(),
                ethnicityUpdateRequestDTO.getCode(), Ethnicity.class);

        toBeUpdatedEthnicity.setName(toUpperCase(ethnicityUpdateRequestDTO.getName()));

        toBeUpdatedEthnicity.setCode(toUpperCase(ethnicityUpdateRequestDTO.getCode()));

        saveEthnicity(updateEthnicity.apply(toBeUpdatedEthnicity,
                ConvertToDeleteRequestDTO.apply(ethnicityUpdateRequestDTO.getRemarks(),
                        ethnicityUpdateRequestDTO.getStatus())));

        log.info(UPDATING_PROCESS_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public EthnicityResponseDTO fetchEthnicityDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, ETHNICITY);

        EthnicityResponseDTO ethnicityResponseDTO = ethnicityRepository.fetchEthnicityDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

        return ethnicityResponseDTO;

    }

    @Override
    public Ethnicity fetchEthnicity(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_ENTITY_PROCESS_STARTED, ETHNICITY);

        Ethnicity ethnicity = ethnicityRepository.fetchEthnicityById(id)
                .orElseThrow(() -> new NoContentFoundException(Ethnicity.class, "id", id.toString()));

        log.info(FETCHING_ENTITY_PROCESS_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

        return ethnicity;

    }

    @Override
    public List<EthnicityDropDownResponseDTO> fetchActiveDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, ETHNICITY);


        List<EthnicityDropDownResponseDTO> dropDownResponseDTOS = ethnicityRepository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Ethnicity.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<EthnicityDropDownResponseDTO> fetchDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, ETHNICITY);


        List<EthnicityDropDownResponseDTO> dropDownResponseDTOS = ethnicityRepository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(Ethnicity.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }


    @Override
    public List<EthnicityMinimalResponseDTO> searchEthnicity(
            EthnicitySearchRequestDTO ethnicitySearchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, ETHNICITY);


        List<EthnicityMinimalResponseDTO> minimalResponseDTOS = ethnicityRepository
                .searchEthnicity(ethnicitySearchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, ETHNICITY, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }


    public Ethnicity fetchEthnicityById(Long id) {
        return ethnicityRepository.fetchEthnicityById(id).orElseThrow(() ->
                new NoContentFoundException(Ethnicity.class, "id", id.toString()));
    }

    public void saveEthnicity(Ethnicity ethnicity) {
        ethnicityRepository.save(ethnicity);
    }

}
