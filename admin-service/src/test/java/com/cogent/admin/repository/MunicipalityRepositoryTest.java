package com.cogent.admin.repository;

import com.cogent.persistence.model.Municipality;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.municipality.MunicipalityResponseUtils.getMunicipality;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author smriti on 2019-09-15
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MunicipalityRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchAdminCategoryById() {
        String sql = "SELECT m FROM Municipality m WHERE m.status!='D' AND m.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Municipality expected = getMunicipality();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }
}
