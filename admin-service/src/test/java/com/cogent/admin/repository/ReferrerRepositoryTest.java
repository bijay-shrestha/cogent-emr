package com.cogent.admin.repository;

import com.cogent.persistence.model.Referrer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.referrer.ReferrerResponseUtils.getReferrer;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static org.junit.Assert.assertNotNull;

/**
 * @author Rupak
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ReferrerRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchTitleById() {
        String sql = "SELECT r FROM Referrer r WHERE r.status!='D' AND r.id=:id ";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Referrer expected = getReferrer();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));


    }


}
