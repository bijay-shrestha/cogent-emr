package com.cogent.admin.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DistrictRepositoryTest {
    @Autowired
    DistrictRepository districtRepository;

    @Test
    public void testCheckSubDepartmentNameIfExist() {
        Long count = districtRepository
                .checkDistrictNameIfExist(1L, "Kathamndu");

        Assert.assertTrue(count == 0L);
    }
}
