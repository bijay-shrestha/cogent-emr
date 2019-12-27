package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ForgotPasswordRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.ForgotPasswordMessages.INVALID_RESET_CODE;
import static com.cogent.admin.constants.QueryConstants.RESET_CODE;
import static com.cogent.admin.query.ForgotPasswordVerificationQuery.QUERY_TO_FETCH_EXPIRATION_TIME;
import static com.cogent.admin.utils.QueryUtils.getQuery;

/**
 * @author smriti on 2019-09-20
 */
@Repository
@Transactional(readOnly = true)
public class ForgotPasswordRepositoryCustomImpl implements ForgotPasswordRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Object findByResetCode(String resetCode) {

        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_EXPIRATION_TIME)
                .setParameter(RESET_CODE, resetCode);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw RESET_CODE_NOT_FOUND.apply(resetCode);
        }
    }

    private Function<String, NoContentFoundException> RESET_CODE_NOT_FOUND = (resetCode) -> {
        throw new NoContentFoundException(INVALID_RESET_CODE, "resetCode", resetCode);
    };

}
