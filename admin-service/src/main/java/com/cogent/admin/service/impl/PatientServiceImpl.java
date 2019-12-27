package com.cogent.admin.service.impl;

import com.cogent.admin.dto.request.patient.PatientRequestDTO;
import com.cogent.admin.dto.response.patient.ResponseDTO;
import com.cogent.admin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cogent.admin.utils.PatientUtils.parseToPatientResponseDTO;

/**
 * @author Sauravi Thapa 9/29/19
 */

@Slf4j
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final MunicipalityService municipalityService;

    private final NationalityService nationalityService;


    private final SurnameService surnameService;

    private final TitleService titleService;

    private final CategoryService categoryService;

    private final ReligionService religionService;

    private final MaritalStatusService maritalStatusService;

    public PatientServiceImpl(MunicipalityService municipalityService,
                              NationalityService nationalityService,
                              SurnameService surnameService,
                              TitleService titleService,
                              CategoryService categoryService,
                              ReligionService religionService,
                              MaritalStatusService maritalStatusService) {
        this.municipalityService = municipalityService;
        this.nationalityService = nationalityService;
        this.surnameService = surnameService;
        this.titleService = titleService;
        this.categoryService = categoryService;
        this.religionService = religionService;
        this.maritalStatusService = maritalStatusService;


    }

    @Override
    public ResponseDTO getPatientDetails(PatientRequestDTO requestDTO) {
        ResponseDTO patientResponseDTO = parseToPatientResponseDTO(
                nationalityService.fetchNatioanlity(requestDTO.getNationalityId()),
                municipalityService.fetchMunicipalityById(requestDTO.getMunicipalityId()),
                surnameService.fetchSurname(requestDTO.getSurnameId()),
                religionService.fetchReligion(requestDTO.getReligionId()),
                maritalStatusService.fetchMaritalStatus(requestDTO.getMaritalStatusId()),
                titleService.fetchTitle(requestDTO.getTitleId()),
                categoryService.fetchActiveCategoryEntity(requestDTO.getCategoryId()));

        return patientResponseDTO;
    }
}
