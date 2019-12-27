package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.request.drug.DrugUpdateRequestDTO;
import com.cogent.admin.dto.response.drug.DrugMinimalResponseDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DrugRepository;
import com.cogent.admin.service.DrugService;
import com.cogent.persistence.model.Drug;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.DrugLog.DRUG;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DrugUtils.convertToDrug;
import static com.cogent.admin.utils.DrugUtils.updateDrug;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

@Slf4j
@Service
@Transactional
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

    public DrugServiceImpl(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Override
    public void createDrug(DrugRequestDTO drugRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DRUG);

        validatetDuplicityByNameOrCode(drugRepository.fetchDrugByNameOrCode(
                drugRequestDTO.getName(), drugRequestDTO.getCode()),
                drugRequestDTO.getName(),
                drugRequestDTO.getCode(), Drug.class);

        saveDrug(convertToDrug(drugRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteDrug(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DRUG);

        Drug drug = fetchDrugById(deleteRequestDTO.getId());

        updateDrug.apply(drug, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateDrug(DrugUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DRUG);

        Drug drug = fetchDrugById(requestDTO.getId());

        validatetDuplicityByNameOrCode(drugRepository.checkIfDrugNameAndCodeExists(
                requestDTO.getId(), requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), Drug.class);

        drug.setName(toUpperCase(requestDTO.getName()));

        drug.setCode(toUpperCase(requestDTO.getCode()));

        updateDrug.apply(drug, ConvertToDeleteRequestDTO
                .apply(requestDTO.getRemarks(), requestDTO.getStatus()));

        log.info(UPDATING_PROCESS_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<DrugMinimalResponseDTO> searchDrug(
            DrugSearchRequestDTO drugSearchRequestDTO,
            Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, DRUG);

        List<DrugMinimalResponseDTO> minimalResponseDTOS = drugRepository
                .searchDrug(drugSearchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public DrugResponseDTO fetchDrugDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DRUG);

        DrugResponseDTO drugResponseDTO = drugRepository.fetchDrugDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

        return drugResponseDTO;

    }

    @Override
    public List<DropDownResponseDTO> drugDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DRUG);

        List<DropDownResponseDTO> dropDownResponseDTOS = drugRepository
                .fetchDropDownList().orElseThrow(() ->
                        new NoContentFoundException(Drug.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<DropDownResponseDTO> activeDrugDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DRUG);

        List<DropDownResponseDTO> dropDownResponseDTOS = drugRepository.fetchActiveDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Drug.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DRUG, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    public void saveDrug(Drug drug) {
        drugRepository.save(drug);
    }

    public Drug fetchDrugById(Long id) {
        return drugRepository.fetchDrugById(id).orElseThrow(() ->
                new NoContentFoundException(Drug.class, "id", id.toString()));
    }
}
