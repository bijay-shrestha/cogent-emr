package com.cogent.admin.repository.custom;

import com.cogent.persistence.model.Ward_Unit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sauravi Thapa 10/22/19
 */
@Repository
@Qualifier("wardUnitRepositoryCustom")
public interface WardUnitRepositoryCustom {
    List<Ward_Unit> fetchWardUnitByWardId( Long id);

    Ward_Unit fetchWardUnitByUnitAndWardId( Long unitId,  Long wardId);
}
