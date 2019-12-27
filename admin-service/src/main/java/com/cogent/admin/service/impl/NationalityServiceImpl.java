package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityUpdateRequestDTO;
import com.cogent.admin.dto.response.nationality.NationalityDropDownResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityMinimalResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.NationalityRepository;
import com.cogent.admin.service.NationalityService;
import com.cogent.persistence.model.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.NationalityLog.NATIONALITY;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NationalityUtils.convertToNationalityInfo;
import static com.cogent.admin.utils.NationalityUtils.updateNationality;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

@Slf4j
@Service
@Transactional
public class NationalityServiceImpl implements NationalityService {

    private final NationalityRepository nationalityRepository;

    public NationalityServiceImpl(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public void createNationality(NationalityRequestDTO nationalityRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, NATIONALITY);

        validateName(nationalityRequestDTO.getName());

        save(convertToNationalityInfo(nationalityRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteNationality(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, NATIONALITY);

        save(updateNationality.apply(fetchNationlaityById(deleteRequestDTO.getId()), deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateNationality(NationalityUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, NATIONALITY);

        Nationality toBeUpdatedNatioanlity = fetchNationlaityById(updateRequestDTO.getId());

        validateNameToUpdate(updateRequestDTO.getId(), updateRequestDTO.getName());

        toBeUpdatedNatioanlity.setName(toUpperCase(updateRequestDTO.getName()));

        save(updateNationality.apply(toBeUpdatedNatioanlity,
                ConvertToDeleteRequestDTO.apply(updateRequestDTO.getRemarks(),
                        updateRequestDTO.getStatus())));

        log.info(UPDATING_PROCESS_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<NationalityMinimalResponseDTO> searchNationality(NationalitySearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, NATIONALITY);


        List<NationalityMinimalResponseDTO> minimalResponseDTOS = nationalityRepository
                .searchNationality(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public NationalityResponseDTO fetchNatioanlityDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, NATIONALITY);

        NationalityResponseDTO responseDTO = nationalityRepository.fetchNationalityDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public Nationality fetchNatioanlity(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, NATIONALITY);

        Nationality nationality = nationalityRepository.fetchActiveNationalityById(id).orElseThrow(() ->
                new NoContentFoundException(Nationality.class));

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

        return nationality;

    }

    @Override
    public List<NationalityDropDownResponseDTO> fetchActiveDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, NATIONALITY);


        List<NationalityDropDownResponseDTO> dropDownResponseDTOS = nationalityRepository.fetchActiveDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Nationality.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<NationalityDropDownResponseDTO> fetchDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, NATIONALITY);


        List<NationalityDropDownResponseDTO> dropDownResponseDTOS = nationalityRepository.fetchDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Nationality.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, NATIONALITY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    public Nationality fetchNationlaityById(Long id) {
        return nationalityRepository.fetchNationalityById(id).orElseThrow(() ->
                new NoContentFoundException(Nationality.class, "id", id.toString()));
    }

    public void validateName(String name) {
        if (nationalityRepository.fetchNationalityCountByName(name) == 1L)
            throw new DataDuplicationException(Nationality.class
                    , NAME_DUPLICATION_MESSAGE + name
                    , NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    public void validateNameToUpdate(Long id, String name) {
        if (nationalityRepository.checkNationalityNameIfExist(id, name) == 1L) {
            throw new DataDuplicationException(Nationality.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
        }
    }

    public void save(Nationality nationality) {
        nationalityRepository.save(nationality);
    }
}
