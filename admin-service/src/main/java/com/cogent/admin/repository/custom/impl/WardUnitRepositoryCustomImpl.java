package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.WardUnitRepositoryCustom;
import com.cogent.persistence.model.Ward_Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.WardUnitQuery.FETCH_WARD_UNIT_BY_ID;
import static com.cogent.admin.query.WardUnitQuery.FETCH_WARD_UNIT_BY_UNIT_AND_WARD_ID;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 10/22/19
 */

@Repository
@Transactional
@Slf4j
public class WardUnitRepositoryCustomImpl implements WardUnitRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Ward_Unit> fetchWardUnitByWardId(Long id) {

        Query query = createQuery.apply(entityManager,FETCH_WARD_UNIT_BY_ID)
                .setParameter(ID,id);

        List<Ward_Unit> wardUnits = transformQueryToResultList(query,
                Ward_Unit.class);

        return wardUnits;
    }

    @Override
    public Ward_Unit fetchWardUnitByUnitAndWardId(Long unitId, Long wardId) {
        Query query = createQuery.apply(entityManager,FETCH_WARD_UNIT_BY_UNIT_AND_WARD_ID)
                .setParameter(WARD_ID,wardId)
                .setParameter(UNIT_ID,unitId);
        try {
            Ward_Unit wardUnit= transformQueryToSingleResult(query,
                    Ward_Unit.class);
            return wardUnit;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Ward_Unit.class);
        }
    }
}
