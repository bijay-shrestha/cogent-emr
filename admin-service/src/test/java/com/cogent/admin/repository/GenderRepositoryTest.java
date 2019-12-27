package com.cogent.admin.repository;

import com.cogent.persistence.model.Gender;
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

import static com.cogent.admin.query.GenderQuery.QUERY_TO_FETCH_ACTIVE_GENDER;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertFalse;

/**
 * @author smriti on 08/11/2019
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class GenderRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    private GenderRepository genderRepository;

    @Test
    public void fetchActiveGender() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_GENDER);
        List results = query.getResultList();
        assertFalse(results.isEmpty());
    }

    @Test
    public void fetchGenderById() {
        Optional<Gender> gender = genderRepository.fetchActiveGenderById(1L);

        assertNotNull(gender);
    }
}
