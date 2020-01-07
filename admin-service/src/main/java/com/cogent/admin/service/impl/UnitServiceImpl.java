package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitRequestDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.admin.dto.response.unit.UnitMinimalResponseDTO;
import com.cogent.admin.dto.response.unit.UnitResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.UnitRepository;
import com.cogent.admin.repository.WardRepository;
import com.cogent.admin.service.UnitService;
import com.cogent.persistence.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.UnitLog.UNIT;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.LogLevel.Level.*;
import static com.cogent.admin.utils.LogLevel.log;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.UnitUtils.*;

/**
 * @author Sauravi Thapa 10/13/19
 */

@Slf4j
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    private final WardRepository wardRepository;

    public UnitServiceImpl(UnitRepository unitRepository, WardRepository wardRepository) {
        this.unitRepository = unitRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public void createUnit(UnitRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(SAVING_PROCESS_STARTED, UNIT);

        log(log, INFO, SAVING_PROCESS_STARTED + "INFO");

        validatetDuplicityByNameOrCode(unitRepository.fetchUnitByNameOrCode
                        (requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), Unit.class);

        save(parseToUnit.apply(requestDTO));

        UnitServiceImpl.log.info(SAVING_PROCESS_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteUnit(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(DELETING_PROCESS_STARTED, UNIT);

        save(deleteUnit.apply(fetchUnitById(deleteRequestDTO.getId()), deleteRequestDTO));

        UnitServiceImpl.log.info(DELETING_PROCESS_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateUnit(UnitUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(UPDATING_PROCESS_STARTED, UNIT);

        Unit toBeUpdatedUnit = fetchUnitById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(unitRepository.checkUnitNameAndCodeIfExist(
                updateRequestDTO.getId(), updateRequestDTO.getName(), updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), Unit.class);

        save(updateToUnit(toBeUpdatedUnit, updateRequestDTO));

        UnitServiceImpl.log.info(UPDATING_PROCESS_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<UnitMinimalResponseDTO> searchUnit(UnitSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(FETCHING_MINIMAL_PROCESS_STARTED, UNIT);


        List<UnitMinimalResponseDTO> minimalResponseDTOS = unitRepository
                .searchUnit(searchRequestDTO, pageable);

        UnitServiceImpl.log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public UnitResponseDTO fetchUnitDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(FETCHING_DETAIL_PROCESS_STARTED, UNIT);

        UnitResponseDTO responseDTO = unitRepository.fetchUnitDetails(id);

        UnitServiceImpl.log.info(FETCHING_DETAIL_PROCESS_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, UNIT);


        List<DropDownResponseDTO> dropDownResponseDTOS = unitRepository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Unit.class));

        UnitServiceImpl.log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        UnitServiceImpl.log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, UNIT);


        List<DropDownResponseDTO> dropDownResponseDTOS = unitRepository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(Unit.class));

        UnitServiceImpl.log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, UNIT, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    public Unit fetchUnitById(Long id) {
        return unitRepository.fetchUnitById(id).orElseThrow(() ->
                new NoContentFoundException(Unit.class, "id", id.toString()));
    }

    public void save(Unit unit) {
        unitRepository.save(unit);
    }
}
