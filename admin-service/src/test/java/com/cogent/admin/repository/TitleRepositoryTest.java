package com.cogent.admin.repository;

import com.cogent.persistence.model.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.title.TitleResponseUtils.getTitle;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TitleRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchTitleById() {
        String sql = "SELECT t FROM Title t WHERE t.status!='D' AND t.id=:id ";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Title expected = getTitle();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));


    }
}
