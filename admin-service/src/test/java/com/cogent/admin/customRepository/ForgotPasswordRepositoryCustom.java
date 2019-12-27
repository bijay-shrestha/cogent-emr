package com.cogent.admin.customRepository;

import com.cogent.admin.repository.custom.impl.ForgotPasswordRepositoryCustomImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;

import static com.cogent.admin.constants.QueryConstants.RESET_CODE;
import static com.cogent.admin.dto.forgotPassword.ForgotPasswordRequestUtils.getResetCode;
import static com.cogent.admin.query.ForgotPasswordVerificationQuery.QUERY_TO_FETCH_EXPIRATION_TIME;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 2019-09-21
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ForgotPasswordRepositoryCustom {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ForgotPasswordRepositoryCustomImpl forgotPasswordRepositoryCustom;

    @Test
    public void findVerificationByResetCode() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_EXPIRATION_TIME)
                .setParameter(RESET_CODE, getResetCode());

        Object result = query.getSingleResult();

        assertNotNull(result);
    }

}
