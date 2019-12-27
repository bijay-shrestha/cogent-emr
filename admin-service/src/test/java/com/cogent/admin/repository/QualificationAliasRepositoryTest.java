package com.cogent.admin.repository;

import com.cogent.persistence.model.QualificationAlias;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.query.QualificationAliasQuery.QUERY_TO_FETCH_ACTIVE_QUALIFICATION_ALIAS;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertFalse;

/**
 * @author smriti on 11/11/2019
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class QualificationAliasRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    private QualificationAliasRepository qualificationAliasRepository;

    @Test
    public void fetchActiveQualificationAlias() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_QUALIFICATION_ALIAS);
        List results = query.getResultList();
        assertFalse(results.isEmpty());
    }

    @Test
    public void fetchQualificationAliasById() {
        Optional<QualificationAlias> qualificationAlias =
                qualificationAliasRepository.fetchActiveQualificationAliasById(1L);

        assertNotNull(qualificationAlias);
    }
}
