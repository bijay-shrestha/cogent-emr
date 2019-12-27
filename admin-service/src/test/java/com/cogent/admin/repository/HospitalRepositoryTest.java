package com.cogent.admin.repository;

import com.cogent.persistence.model.Hospital;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.dto.hospital.HospitalResponseUtils.getHospital;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static org.junit.Assert.assertNotNull;

/**
 * @author Rupak
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class HospitalRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchHospitalById() {
        String sql = "SELECT h FROM Hospital h WHERE h.status!='D' AND h.id=:id ";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Hospital expected = getHospital();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }

}
