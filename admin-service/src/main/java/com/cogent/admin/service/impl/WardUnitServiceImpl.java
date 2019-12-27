package com.cogent.admin.service.impl;

import com.cogent.admin.dto.request.ward.WardUnitDeleteRequestDTO;
import com.cogent.admin.repository.WardUnitRepository;
import com.cogent.admin.service.WardUnitService;
import com.cogent.persistence.model.Ward_Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cogent.admin.log.CommonLogConstant.DELETING_PROCESS_COMPLETED;
import static com.cogent.admin.log.CommonLogConstant.DELETING_PROCESS_STARTED;
import static com.cogent.admin.log.constants.WardLog.WARD_UNIT;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.WardUnitUtils.deleteWardUnit;

/**
 * @author Sauravi Thapa 10/21/19
 */

@Service
@Slf4j
@Transactional
public class WardUnitServiceImpl implements WardUnitService {

    private final WardUnitRepository wardUnitRepository;

    public WardUnitServiceImpl(WardUnitRepository wardUnitRepository) {
        this.wardUnitRepository = wardUnitRepository;
    }

    @Override
    public void delete(WardUnitDeleteRequestDTO wardUnitDeleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, WARD_UNIT);

        save(deleteWardUnit.apply(fetchWardUnitByUnitAndWardId(wardUnitDeleteRequestDTO.getUnitID(),
                wardUnitDeleteRequestDTO.getWardId()), wardUnitDeleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, WARD_UNIT, getDifferenceBetweenTwoTime(startTime));

    }

    public Ward_Unit fetchWardUnitByUnitAndWardId(Long unitId, Long wardId) {
        return wardUnitRepository.fetchWardUnitByUnitAndWardId(unitId, wardId);
    }

    public void save(Ward_Unit wardUnit) {
        wardUnitRepository.save(wardUnit);
    }


}
