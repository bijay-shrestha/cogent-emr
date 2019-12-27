package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesMinimalResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.admin.repository.ProvincesRepository;
import com.cogent.admin.service.DistrictService;
import com.cogent.admin.service.ProvincesService;
import com.cogent.persistence.model.Provinces;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.ProvincesMessages.PROVINCES_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.ProvincesMessages.PROVINCES_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ProvincesLog.PROVINCES;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DistrictUtils.distictDeleteRequestDTO;
import static com.cogent.admin.utils.DistrictUtils.parseToUpdateListOfDistrict;
import static com.cogent.admin.utils.ProvincesUtils.convertToProvincesInfo;
import static com.cogent.admin.utils.ProvincesUtils.updateProvinces;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

@Slf4j
@Service
@Transactional
public class ProvincesServiceImpl implements ProvincesService {

    private final ProvincesRepository provincesRepository;

    private final DistrictRepository districtRepository;

    private final DistrictService districtService;

    public ProvincesServiceImpl(ProvincesRepository provincesRepository, DistrictRepository districtRepository,
                                DistrictService districtService) {
        this.provincesRepository = provincesRepository;
        this.districtRepository = districtRepository;
        this.districtService = districtService;
    }

    @Override
    public void createProvinces(ProvincesRequestDTO provincesRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, PROVINCES);

        validateName(provincesRequestDTO.getName());

        saveProvinces(convertToProvincesInfo(provincesRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<ProvincesDropDownResponseDTO> provincesDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, PROVINCES);


        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = provincesRepository
                .fetchDropDownList().orElseThrow(() ->
                        new NoContentFoundException(Provinces.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<ProvincesDropDownResponseDTO> activeProvinceDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, PROVINCES);


        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = provincesRepository
                .fetchActiveDropDownList().orElseThrow(() ->
                        new NoContentFoundException(Provinces.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<ProvincesMinimalResponseDTO> searchProvinces(ProvincesSearchRequestDTO searchRequestDTO,
                                                             Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, PROVINCES);

        List<ProvincesMinimalResponseDTO> minimalResponseDTOS = provincesRepository
                .searchProvinces(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public ProvincesResponseDTO fetchProvincesDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, PROVINCES);

        ProvincesResponseDTO provincesResponseDTO = provincesRepository.fetchProvincesDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

        return provincesResponseDTO;

    }

    @Override
    public void deleteProvinces(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, PROVINCES);

        saveProvinces(updateProvinces.apply(fetchProvinceById(deleteRequestDTO.getId()), deleteRequestDTO));

        districtToDelete(distictDeleteRequestDTO.apply(deleteRequestDTO
                , districtRepository.fetchDistrictToDeleteByProvinceId(deleteRequestDTO.getId())));

        log.info(DELETING_PROCESS_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateProvinces(ProvincesUpdateRequestDTO provincesUpdateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, PROVINCES);

        Provinces provincestoUpdate = fetchProvinceById(provincesUpdateRequestDTO.getId());

        validateNameToUpdate(provincesUpdateRequestDTO.getId(), provincesUpdateRequestDTO.getName());

        saveDistrictToUpdate(provincesUpdateRequestDTO);

        Provinces provinces = updateProvinces.apply(provincestoUpdate,
                ConvertToDeleteRequestDTO.apply(provincesUpdateRequestDTO.getRemarks(),
                        provincesUpdateRequestDTO.getStatus()));

        provinces.setName(toUpperCase(provincesUpdateRequestDTO.getName()));

        saveProvinces(provinces);

        log.info(UPDATING_PROCESS_COMPLETED, PROVINCES, getDifferenceBetweenTwoTime(startTime));

    }

    public void validateNameToUpdate(Long id, String name) {
        if (provincesRepository.checkProvincesNameIfExist(id, name) == 1L) {
            throw new DataDuplicationException(Provinces.class, PROVINCES_DUPLICATION_MESSAGE + name,
                    PROVINCES_DUPLICATION_DEBUG_MESSAGE + name);
        }

    }

    public void saveProvinces(Provinces provinces) {
        provincesRepository.save(provinces);
    }

    public void validateName(String name) {
        if (provincesRepository.fetchCountByName(name) == 1L)
            throw new DataDuplicationException(Provinces.class
                    , NAME_DUPLICATION_MESSAGE + name
                    , NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    public Provinces fetchProvinceById(Long id) {
        return provincesRepository.fetchprovincesById(id).orElseThrow(() ->
                new NoContentFoundException(Provinces.class, "id", id.toString()));
    }

    public void saveDistrictToUpdate(ProvincesUpdateRequestDTO provincesUpdateRequestDTO) {
        districtRepository.saveAll(parseToUpdateListOfDistrict
                .apply(districtRepository
                        .fetchDistrictByProvincesId(
                                provincesUpdateRequestDTO.getId()), provincesUpdateRequestDTO));
    }

    public void districtToDelete(List<DeleteRequestDTO> deleteRequestDTOS) {
        deleteRequestDTOS.forEach(deleteRequestDTO -> {
            districtService.deleteDistrict(deleteRequestDTO);
        });
    }
}
