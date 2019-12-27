package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.district.DistrictRequestDTO;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.request.district.DistrictUpdateRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.admin.repository.ProvincesRepository;
import com.cogent.admin.service.DistrictService;
import com.cogent.persistence.model.District;
import com.cogent.persistence.model.Provinces;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.DistrictLog.DISTRICT;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DistrictUtils.parseToDistrict;
import static com.cogent.admin.utils.DistrictUtils.updateDistrict;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

@Slf4j
@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    private final ProvincesRepository provincesRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository, ProvincesRepository provincesRepository) {
        this.districtRepository = districtRepository;
        this.provincesRepository = provincesRepository;
    }

    @Override
    public void createDistrict(DistrictRequestDTO districtRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DISTRICT);

        validateName(districtRequestDTO.getName());

        saveDistrict(parseToDistrict.apply(districtRequestDTO,
                checkActiveProvinces(districtRequestDTO.getProvinceId())));

        log.info(SAVING_PROCESS_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<DistrictDropDownResponseDTO> districtDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DISTRICT);

        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = districtRepository.fetchDropDownList()
                .orElseThrow(() -> new NoContentFoundException(District.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<DistrictDropDownResponseDTO> activeDistrictDropdown() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DISTRICT);

        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = districtRepository.fetchActiveDropDownList()
                .orElseThrow(() -> new NoContentFoundException(District.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public DistrictResponseDTO fetchDistrictDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DISTRICT);

        DistrictResponseDTO responseDTO = districtRepository.fetchDistrictDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public List<DistrictMinimalResponseDTO> searchDistrict(DistrictSearchRequestDTO districtSearchRequestDTO,
                                                           Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, DISTRICT);

        List<DistrictMinimalResponseDTO> minimalResponseDTOS = districtRepository.searchDistrict(districtSearchRequestDTO
                , pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public void deleteDistrict(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DISTRICT);

        saveDistrict(updateDistrict.apply(fetchDistrictById(deleteRequestDTO.getId()), deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateDistrict(DistrictUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DISTRICT);

        validateDistrictByIdAndName(updateRequestDTO.getId(), updateRequestDTO.getName());

        District district = updateDistrict.apply(fetchDistrictById(updateRequestDTO.getId()),
                ConvertToDeleteRequestDTO(updateRequestDTO));

        district.setName(toUpperCase(updateRequestDTO.getName()));

        district.setProvinces(checkActiveProvinces(updateRequestDTO.getProvincesId()));

        saveDistrict(district);

        log.info(UPDATING_PROCESS_COMPLETED, DISTRICT, getDifferenceBetweenTwoTime(startTime));

    }

    public void validateName(String name) {
        if (districtRepository.fetchDistrictCountByName(name) != 1L)
            throw new DataDuplicationException(District.class
                    , NAME_DUPLICATION_MESSAGE + name
                    , NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    public Provinces checkActiveProvinces(Long id) {
        return provincesRepository.findActiveProvincesById(id).orElseThrow(() ->
                new NoContentFoundException(Provinces.class, "id", id.toString()));
    }

    public void saveDistrict(District district) {
        districtRepository.save(district);
    }

    public District fetchDistrictById(Long id) {
        return districtRepository.fetchDistrictById(id).orElseThrow(() ->
                new NoContentFoundException(District.class, "id", id.toString()));

    }

    public void validateDistrictByIdAndName(Long id, String name) {
        if (districtRepository.checkDistrictNameIfExist(id, name) != 1L)
            throw new DataDuplicationException(District.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    public DeleteRequestDTO ConvertToDeleteRequestDTO(DistrictUpdateRequestDTO updateRequestDTO) {
        return ConvertToDeleteRequestDTO
                .apply(updateRequestDTO.getRemarks(),
                        updateRequestDTO.getStatus());
    }

}


