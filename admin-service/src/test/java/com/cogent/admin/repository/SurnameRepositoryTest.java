package com.cogent.admin.repository;

import com.cogent.persistence.model.Surname;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.surname.SurnameResponseUtils.getSurname;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author smriti on 2019-09-12
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SurnameRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchSurnameById() {
        String sql = "SELECT s FROM Surname s WHERE s.status!='D' AND s.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Surname expected = getSurname();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }
}
