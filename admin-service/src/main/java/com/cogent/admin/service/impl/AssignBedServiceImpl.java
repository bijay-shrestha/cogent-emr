package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedMinimalResponseDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AssignBedRepository;
import com.cogent.admin.repository.BedRepository;
import com.cogent.admin.repository.WardRepository;
import com.cogent.admin.repository.WardUnitRepository;
import com.cogent.admin.service.AssignBedService;
import com.cogent.admin.utils.AssignBedUtils;
import com.cogent.persistence.model.AssignBed;
import com.cogent.persistence.model.Bed;
import com.cogent.persistence.model.Ward;
import com.cogent.persistence.model.Ward_Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.AssignBedMessages.BED_ALREADY_EXIST;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.AssignBedLog.ASSIGN_BED;
import static com.cogent.admin.utils.AssignBedUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author Sauravi Thapa 11/7/19
 */

@Service
@Transactional
@Slf4j


public class AssignBedServiceImpl implements AssignBedService {

    private final AssignBedRepository assignBedRepository;

    private final WardUnitRepository wardUnitRepository;

    private final BedRepository bedRepository;

    private final WardRepository wardRepository;

    public AssignBedServiceImpl(AssignBedRepository assignBedRepository,
                                WardUnitRepository wardUnitRepository,
                                BedRepository bedRepository,
                                WardRepository wardRepository) {
        this.assignBedRepository = assignBedRepository;
        this.wardUnitRepository = wardUnitRepository;
        this.bedRepository = bedRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public void createAssignBed(AssignBedRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, ASSIGN_BED);

        checkIfBedExists(requestDTO.getBedId(), requestDTO.getWardId(), requestDTO.getUnitId());

        if (requestDTO.getUnitId() != null) {
            saveWithUnit(requestDTO);
        } else {
            saveWithoutUnit(requestDTO);
        }

        log.info(SAVING_PROCESS_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void deleteAssignedBed(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, ASSIGN_BED);

        save(deleteAssignedBed.apply(fetchAssignedBed(deleteRequestDTO.getId()), deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateAssignedBed(AssignBedUpdateRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, ASSIGN_BED);

        AssignBed assignBedToUpdate = fetchAssignedBed(requestDTO.getId());

        checkIfBedExists(requestDTO.getBedId(), requestDTO.getWardId(), requestDTO.getUnitId());

        if (requestDTO.getUnitId() != null) {
            updateWithUnit(requestDTO, assignBedToUpdate);
        } else {
            updateWithoutUnit(requestDTO, assignBedToUpdate);
        }

        log.info(UPDATING_PROCESS_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<AssignBedMinimalResponseDTO> searchAssignedBed(AssignBedSearchRequestDTO searchRequestDTO,
                                                               Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, ASSIGN_BED);

        List<AssignBedMinimalResponseDTO> minimalResponseDTOS = assignBedRepository.searchAssignedBed(searchRequestDTO,
                pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }


    @Override
    public AssignBedResponseDTO fetchAssignedBedDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, ASSIGN_BED);

        AssignBedResponseDTO responseDTO = assignBedRepository.fetchAssignBedDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList(Long wardId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, ASSIGN_BED);


        List<DropDownResponseDTO> dropDownResponseDTOS = assignBedRepository.activeDropDownList(wardId)
                .orElseThrow(() -> new NoContentFoundException(Bed.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList(Long wardId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, ASSIGN_BED);

        List<DropDownResponseDTO> dropDownResponseDTOS = assignBedRepository.dropDownList(wardId)
                .orElseThrow(() -> new NoContentFoundException(Bed.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, ASSIGN_BED, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    public void checkIfBedExists(Long bedId, Long wardId, Long unitId) {
        if (assignBedRepository.checkIfBedExixts(bedId, wardId, unitId) == 1)
            throw new DataDuplicationException(BED_ALREADY_EXIST);

    }

    public void saveWithUnit(AssignBedRequestDTO requestDTO) {

        Ward_Unit wardUnit = getActiveWardAndUnit(requestDTO.getUnitId(), requestDTO.getWardId());

        save(parseToAssignedBed(requestDTO,
                getActiveBed(requestDTO.getBedId()), wardUnit.getWard(), wardUnit.getUnit()
        ));
    }

    public void updateWithUnit(AssignBedUpdateRequestDTO requestDTO, AssignBed assignBedToUpdate) {

        Ward_Unit wardUnit = getActiveWardAndUnit(requestDTO.getUnitId(), requestDTO.getWardId());

        save(AssignBedUtils.updateAssignedBed(assignBedToUpdate,
                requestDTO,
                getActiveBed(requestDTO.getBedId()),
                wardUnit.getWard(), wardUnit.getUnit()));

    }

    public void saveWithoutUnit(AssignBedRequestDTO requestDTO) {
        save(parseToAssignedBed(requestDTO,
                getActiveBed(requestDTO.getBedId()), getActiveWard(requestDTO.getWardId()), null));
    }

    public void updateWithoutUnit(AssignBedUpdateRequestDTO requestDTO, AssignBed assignBedToUpdate) {
        save(AssignBedUtils.updateAssignedBed(assignBedToUpdate, requestDTO,
                getActiveBed(requestDTO.getBedId()), getActiveWard(requestDTO.getWardId()), null));
    }

    public AssignBed fetchAssignedBed(Long id) {
        return assignBedRepository.fetchAssignBedById(id);
    }

    public Bed getActiveBed(Long bedId) {
        return bedRepository.fetchActiveBedById(bedId);
    }

    public Ward_Unit getActiveWardAndUnit(Long unitId, Long wardId) {
        return wardUnitRepository.fetchWardUnitByUnitAndWardId(unitId, wardId);
    }

    public Ward getActiveWard(Long wardId) {
        return wardRepository.fetchWardById(wardId)
                .orElseThrow(() -> new NoContentFoundException(Ward.class));
    }

    public void save(AssignBed assignBed) {
        assignBedRepository.save(assignBed);
    }
}
