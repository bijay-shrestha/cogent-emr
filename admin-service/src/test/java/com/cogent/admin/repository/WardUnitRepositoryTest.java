package com.cogent.admin.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author Sauravi Thapa 10/21/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class WardUnitRepositoryTest {

    @Autowired
    private WardUnitRepository wardUnitRepository;

    @Test
    public void fetchWardUnitCountByUnitAndWardId() {
        Long count = wardUnitRepository.fetchWardUnitCountByUnitAndWardId(2L, 1L);

        assertTrue(count != 1L);
    }



}
