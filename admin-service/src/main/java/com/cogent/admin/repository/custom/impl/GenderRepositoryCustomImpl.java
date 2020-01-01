package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.GenderRepositoryCustom;
import com.cogent.persistence.model.Gender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.query.GenderQuery.QUERY_TO_FETCH_ACTIVE_GENDER;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author smriti on 08/11/2019
 */
@Repository
@Transactional(readOnly = true)
public class GenderRepositoryCustomImpl implements GenderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DropDownResponseDTO> fetchActiveGender() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_GENDER);

        List<DropDownResponseDTO> results = transformQueryToResultList(query, DropDownResponseDTO.class);

        if (results.isEmpty()) throw new NoContentFoundException(Gender.class);
        else return results;
    }
}
