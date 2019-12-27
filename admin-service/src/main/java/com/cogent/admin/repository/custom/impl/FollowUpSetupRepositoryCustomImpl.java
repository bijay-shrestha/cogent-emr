package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupMinimalResponseDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.FollowUpSetupRepositoryCustom;
import com.cogent.persistence.model.FollowUpSetup;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.query.FollowUpSetupQuery.QUERY_TO_FETCH_FOLLOW_UP_SETUP;
import static com.cogent.admin.query.FollowUpSetupQuery.QUERY_TO_FETCH_FOLLOW_UP_SETUP_DETAILS;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-11-04
 */
@Repository
@Transactional(readOnly = true)
public class FollowUpSetupRepositoryCustomImpl implements FollowUpSetupRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FollowUpSetupMinimalResponseDTO> fetchFollowUpSetup() {
        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_FOLLOW_UP_SETUP);

        List<FollowUpSetupMinimalResponseDTO> results = transformQueryToResultList(
                query, FollowUpSetupMinimalResponseDTO.class);

        if (results.isEmpty()) throw FOLLOW_UP_SETUP_NOT_FOUND.get();

        return results;
    }

    @Override
    public FollowUpSetupResponseDTO fetchDetailsById(Long id) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_FOLLOW_UP_SETUP_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, FollowUpSetupResponseDTO.class);
        } catch (NoResultException e) {
            throw FOLLOW_UP_SETUP_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private Supplier<NoContentFoundException> FOLLOW_UP_SETUP_NOT_FOUND = ()
            -> new NoContentFoundException(FollowUpSetup.class);

    private Function<Long, NoContentFoundException> FOLLOW_UP_SETUP_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(FollowUpSetup.class, "id", id.toString());
    };
}
