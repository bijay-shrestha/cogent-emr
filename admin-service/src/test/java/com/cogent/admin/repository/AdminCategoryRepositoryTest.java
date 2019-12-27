package com.cogent.admin.repository;

import com.cogent.persistence.model.AdminCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.adminCategory.AdminCategoryResponseUtils.getAdminCategory;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AdminCategoryRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchAdminCategoryById() {
        String sql = "SELECT a FROM AdminCategory a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        AdminCategory expected = getAdminCategory();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }
}
