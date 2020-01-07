package com.cogent.admin.repository;

import com.cogent.admin.dto.response.qualification.QualificationMinimalResponseDTO;
import com.cogent.admin.utils.QueryUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.dto.qualification.QualificationRequestUtils.getQualificationSearchRequestDTO;
import static com.cogent.admin.query.QualificationQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author smriti on 12/11/2019
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class QualificationRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchQualificationById() {
        String sql = "SELECT q FROM Qualification q WHERE q.status!='D' AND q.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        assertNotNull(query);
    }

    @Test
    public void searchQualificationTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_QUALIFICATION.apply
                (getQualificationSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, QualificationMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchQualificationForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_QUALIFICATION_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchQualificationDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_QUALIFICATION_DETAILS)
                .setParameter("id", 1L);

        assertFalse(query.getResultList().isEmpty());
    }
}
