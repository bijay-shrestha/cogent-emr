package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardRequestDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.UnitRepository;
import com.cogent.admin.repository.WardRepository;
import com.cogent.admin.repository.WardUnitRepository;
import com.cogent.admin.service.WardService;
import com.cogent.persistence.model.Unit;
import com.cogent.persistence.model.Ward;
import com.cogent.persistence.model.Ward_Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.StringConstant.D;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.WardLog.WARD;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.WardUnitUtils.deleteWardUnitList;
import static com.cogent.admin.utils.WardUnitUtils.parseToWardUnit;
import static com.cogent.admin.utils.WardUtils.*;

/**
 * @author Sauravi Thapa 10/2/19
 */

@Slf4j
@Service
@Transactional
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;

    private final UnitRepository unitRepository;

    private final WardUnitRepository wardUnitRepository;

    public WardServiceImpl(WardRepository wardRepository, UnitRepository unitRepository, WardUnitRepository wardUnitRepository) {
        this.wardRepository = wardRepository;
        this.unitRepository = unitRepository;
        this.wardUnitRepository = wardUnitRepository;
    }

    @Override
    public void createWard(WardRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, WARD);

        validatetDuplicityByNameOrCode(wardRepository.fetchWardByNameOrCode
                        (requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), Ward.class);

        Ward ward = save(parseToWard(requestDTO));

        save(ward, requestDTO);

        log.info(SAVING_PROCESS_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));


    }

    @Override
    public void deleteWard(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, WARD);

        deleteWard.apply(fetchWardById(deleteRequestDTO.getId()), deleteRequestDTO);

        deleteWardUnitByWardId(deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateWard(WardUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, WARD);

        Ward toBeUpdatedWard = fetchWardById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(wardRepository.checkIfWardNameAndCodeExists(
                updateRequestDTO.getId(), updateRequestDTO.getName(), updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), Ward.class);


        updateWardUnit(updateRequestDTO);

        updateWard.apply(toBeUpdatedWard, updateRequestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<WardMinimalResponseDTO> searchWard(WardSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, WARD);


        List<WardMinimalResponseDTO> minimalResponseDTOS = wardRepository
                .searchWard(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;

    }

    @Override
    public WardResponseDTO fetchWardDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, WARD);

        WardResponseDTO responseDTO = wardRepository.fetchWardDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;

    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, WARD);


        List<DropDownResponseDTO> dropDownResponseDTOS = wardRepository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Ward.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, WARD);


        List<DropDownResponseDTO> dropDownResponseDTOS = wardRepository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(Ward.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, WARD, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;

    }

    public Ward fetchWardById(Long id) {
        return wardRepository.fetchWardById(id).orElseThrow(() ->
                new NoContentFoundException(Ward.class, "id", id.toString()));
    }

    public Unit fetchUnitById(Long id) {
        return unitRepository.fetchUnitById(id).orElseThrow(() ->
                new NoContentFoundException(Unit.class, "id", id.toString()));
    }

    public List<Ward_Unit> fetchWardUnitByWardId(Long wardId) {
        return wardUnitRepository.fetchWardUnitByWardId(wardId);
    }

    public void deleteWardUnitByWardId(DeleteRequestDTO deleteRequestDTO) {
        List<Ward_Unit> wardUnits = deleteWardUnitList.apply(fetchWardUnitByWardId(deleteRequestDTO.getId()), deleteRequestDTO);

        wardUnits.forEach(wardUnit -> save(wardUnit));
    }

    public void updateWardUnit(WardUpdateRequestDTO requestDTO) {
        if (!requestDTO.getHas_Unit()) {
            deleteWardUnitList.apply(fetchWardUnitByWardId(requestDTO.getId()),
                    ConvertToDeleteRequestDTO.apply(requestDTO.getRemarks(),
                            D));
        } else {
            checkAndSaveWardUnit(requestDTO);
        }

    }

    public void checkAndSaveWardUnit(WardUpdateRequestDTO requestDTO) {
        requestDTO.getUnitId().forEach(unitId -> {
            if (wardUnitRepository.fetchWardUnitCountByUnitAndWardId(unitId, requestDTO.getId()) != 1L) {
                save(parseToWardUnit(fetchWardById(requestDTO.getId()), fetchUnitById(unitId), requestDTO.getStatus()));
            }
        });
    }

    public Ward save(Ward ward) {
        return wardRepository.save(ward);

    }

    public void save(Ward_Unit wardUnit) {
        wardUnitRepository.save(wardUnit);
    }

    public void save(Ward ward, WardRequestDTO requestDTO) {
        if (requestDTO.getHas_Unit()) {
            requestDTO.getUnitId().forEach(unitId -> {
                Unit unit = fetchUnitById(unitId);
                save(parseToWardUnit(ward, unit, requestDTO.getStatus()));
            });
        }
    }
}
