package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DoctorTypeRepositoryCustom;
import com.cogent.persistence.model.DoctorType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.query.DoctorTypeQuery.QUERY_TO_FETCH_ACTIVE_DOCTOR_TYPE;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author smriti on 08/11/2019
 */
@Repository
@Transactional(readOnly = true)
public class DoctorTypeRepositoryCustomImpl implements DoctorTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DropDownResponseDTO> fetchActiveDoctorType() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_DOCTOR_TYPE);

        List<DropDownResponseDTO> results = transformQueryToResultList(query, DropDownResponseDTO.class);

        if (results.isEmpty()) throw new NoContentFoundException(DoctorType.class);
        else return results;
    }
}
