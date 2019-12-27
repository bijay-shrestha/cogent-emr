package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityDropdownDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.admin.repository.MunicipalityRepository;
import com.cogent.admin.service.MunicipalityService;
import com.cogent.persistence.model.District;
import com.cogent.persistence.model.Municipality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.MunicipalityLog.MUNICIPALITY;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.MunicipalityUtils.*;

/**
 * @author smriti on 2019-09-15
 */
@Service
@Transactional
@Slf4j
public class MunicipalityServiceImpl implements MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    private final DistrictRepository districtRepository;

    public MunicipalityServiceImpl(MunicipalityRepository municipalityRepository,
                                   DistrictRepository districtRepository) {
        this.municipalityRepository = municipalityRepository;
        this.districtRepository = districtRepository;
    }

    @Override
    public void save(MunicipalityRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, MUNICIPALITY);

        validateName(municipalityRepository.fetchMunicipalityByName(requestDTO.getName()),
                requestDTO.getName());

        District district = findDistrictById(requestDTO.getDistrictId());

        save(convertDTOToMunicipality(requestDTO, district));

        log.info(SAVING_PROCESS_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(MunicipalityUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, MUNICIPALITY);

        Municipality municipality = findById(requestDTO.getId());

        validateName(municipalityRepository.findMunicipalityByIdAndName(requestDTO.getId(),
                requestDTO.getName()), requestDTO.getName());

        District district = findDistrictById(requestDTO.getDistrictId());

        save(convertToUpdatedMunicipality(requestDTO, municipality, district));

        log.info(UPDATING_PROCESS_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, MUNICIPALITY);

        Municipality municipality = findById(deleteRequestDTO.getId());

        save(convertToDeletedMunicipality(municipality, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<MunicipalityMinimalResponseDTO> search(MunicipalitySearchRequestDTO searchRequestDTO,
                                                       Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, MUNICIPALITY);

        List<MunicipalityMinimalResponseDTO> responseDTOS = municipalityRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<MunicipalityDropdownDTO> fetchActiveMunicipalityForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, MUNICIPALITY);

        List<MunicipalityDropdownDTO> responseDTOS = municipalityRepository.fetchActiveMunicipalityForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public MunicipalityResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, MUNICIPALITY);

        MunicipalityResponseDTO responseDTO = municipalityRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public Municipality fetchMunicipalityById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, MUNICIPALITY);

        Municipality municipality = municipalityRepository.findMunicipality(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, MUNICIPALITY, getDifferenceBetweenTwoTime(startTime));

        return municipality;
    }

    private void validateName(Long municipalityCount, String name) {
        if (municipalityCount.intValue() > 0)
            throw new DataDuplicationException(
                    Municipality.class, NAME_DUPLICATION_MESSAGE + name, NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private District findDistrictById(Long id) {
        return districtRepository.findActiveDistrictById(id)
                .orElseThrow(() -> new NoContentFoundException(District.class, "id", id.toString()));
    }

    private void save(Municipality municipality) {
        municipalityRepository.save(municipality);
    }

    public Municipality findById(Long municipalityId) {
        return municipalityRepository.findMunicipalityById(municipalityId)
                .orElseThrow(() -> MUNICIPALITY_WITH_GIVEN_ID_NOT_FOUND.apply(municipalityId));
    }

    private Function<Long, NoContentFoundException> MUNICIPALITY_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Municipality.class, "id", id.toString());
    };
}
