package com.cogent.admin.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author smriti on 2019-08-05
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void fetchAdminById() {
        String sql = "SELECT a FROM Admin a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter(ID, 4L).getSingleResult();

        assertNotNull(query);
    }

}
