package com.cogent.admin.repository;

import com.cogent.persistence.model.FollowUpSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.query.FollowUpSetupQuery.QUERY_TO_FETCH_FOLLOW_UP_SETUP;
import static com.cogent.admin.query.FollowUpSetupQuery.QUERY_TO_FETCH_FOLLOW_UP_SETUP_DETAILS;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 2019-11-04
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class FollowUpSetupRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private FollowUpSetupRepository followUpSetupRepository;

    @Test
    public void fetchFollowUpSetup() {
        Optional<FollowUpSetup> followUpSetup = followUpSetupRepository.findFollowUpSetupById(1L);
        assertNotNull(followUpSetup);
    }

    @Test
    public void searchFollowUpSetup() {
        String query = QUERY_TO_FETCH_FOLLOW_UP_SETUP;

        List resultList = getQuery.apply(testEntityManager, query).getResultList();

        assertNotNull(resultList);
    }

    @Test
    public void fetchFollowUpSetupDetails() {
        String query = QUERY_TO_FETCH_FOLLOW_UP_SETUP_DETAILS;

        Object result = getQuery.apply(testEntityManager, query)
                .setParameter(ID, 1L).getSingleResult();

        assertNotNull(result);
    }
}
