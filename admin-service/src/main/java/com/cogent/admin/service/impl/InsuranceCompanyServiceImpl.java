package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyUpdateRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyMinimalResponseDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.InsuranceCompanyRepository;
import com.cogent.admin.service.InsuranceCompanyService;
import com.cogent.persistence.model.InsuranceCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.InsuranceCompanyLog.INSURANCE_COMPANY;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.InsuranceCompanyUtils.convertToInsuranceCompanyInfo;
import static com.cogent.admin.utils.InsuranceCompanyUtils.updateInsuranceCompany;
import static com.cogent.admin.utils.StringUtil.toUpperCase;


@Service
@Slf4j
@Transactional
public class InsuranceCompanyServiceImpl implements InsuranceCompanyService {

    private final InsuranceCompanyRepository insuranceCompanyRepository;

    public InsuranceCompanyServiceImpl(InsuranceCompanyRepository insuranceCompanyRepository) {
        this.insuranceCompanyRepository = insuranceCompanyRepository;
    }

    @Override
    public void createInsuranceCompany(InsuranceCompanyRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, INSURANCE_COMPANY);

        validateName(requestDTO.getName());

        save(convertToInsuranceCompanyInfo(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteInsuranceCompany(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, INSURANCE_COMPANY);

        save(updateInsuranceCompany.apply(fetchInsuranceCompanyById(deleteRequestDTO.getId()), deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateInsuranceCompany(InsuranceCompanyUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, INSURANCE_COMPANY);

        InsuranceCompany toBeUpdatedInsuranceCompany = fetchInsuranceCompanyById(updateRequestDTO.getId());

        validateNameToUpdate(updateRequestDTO.getId(), updateRequestDTO.getName());

        toBeUpdatedInsuranceCompany.setName(toUpperCase(updateRequestDTO.getName()));

        save(updateInsuranceCompany.apply(toBeUpdatedInsuranceCompany,
                ConvertToDeleteRequestDTO.apply(updateRequestDTO.getRemarks(),
                        updateRequestDTO.getStatus())));

        log.info(UPDATING_PROCESS_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<InsuranceCompanyMinimalResponseDTO> searchInsuranceCompany(
            InsuranceCompanySearchRequestDTO searchRequestDTO,
            Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, INSURANCE_COMPANY);


        List<InsuranceCompanyMinimalResponseDTO> minimalResponseDTOS = insuranceCompanyRepository
                .searchInsuranceCompany(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public InsuranceCompanyResponseDTO fetchInsuranceCompanyDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, INSURANCE_COMPANY);

        InsuranceCompanyResponseDTO responseDTO = insuranceCompanyRepository.fetchInsuranceCompanyDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public InsuranceCompany fetchInsuranceCompany(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, INSURANCE_COMPANY);

        InsuranceCompany insuranceCompany = insuranceCompanyRepository.fetchActiveInsuranceCompanyById(id).orElseThrow(() ->
                new NoContentFoundException(InsuranceCompany.class));

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

        return insuranceCompany;

    }


    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, INSURANCE_COMPANY);


        List<DropDownResponseDTO> dropDownResponseDTOS = insuranceCompanyRepository.fetchDropDownList()
                .orElseThrow(() -> new NoContentFoundException(InsuranceCompany.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, INSURANCE_COMPANY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, INSURANCE_COMPANY);


        List<DropDownResponseDTO> dropDownResponseDTOS = insuranceCompanyRepository.fetchActiveDropDownList()
                .orElseThrow(() -> new NoContentFoundException(InsuranceCompany.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, INSURANCE_COMPANY,
                getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }


    public InsuranceCompany fetchInsuranceCompanyById(Long id) {
        return insuranceCompanyRepository.fetchInsuranceCompanyById(id).orElseThrow(() ->
                new NoContentFoundException(InsuranceCompany.class, "id", id.toString()));
    }

    public void validateNameToUpdate(Long id, String name) {
        if (insuranceCompanyRepository.checkInsuranceCompanyNameIfExist(id, name) == 1L) {
            throw new DataDuplicationException(InsuranceCompany.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
        }

    }

    public void validateName(String name) {
        if (insuranceCompanyRepository.fetchInsuranceCompanyCountByName(name) == 1L)
            throw new DataDuplicationException(InsuranceCompany.class
                    , NAME_DUPLICATION_MESSAGE + name
                    , NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    public void save(InsuranceCompany insuranceCompany) {
        insuranceCompanyRepository.save(insuranceCompany);
    }
}
