package com.cogent.admin.repository;

import com.cogent.admin.utils.QueryUtil;
import com.cogent.persistence.model.Religion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.request.religion.ReligionResponseUtils.getReligion;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ReligionRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchReligionById() {
        String sql = "SELECT r FROM Religion r WHERE r.status!='D' AND r.id=:id ";

        Object query = QueryUtil.getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Religion expected = getReligion();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));


    }

}
