package com.cogent.admin.repository;

import com.cogent.persistence.model.Provinces;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProvincesRepositoryTest {

    @Autowired
    private ProvincesRepository provincesRepository;


    @Test
    public void testFetchprovincesById() {
        Optional<Provinces> provinces = provincesRepository.fetchprovincesById(1L);

        assertNotNull(provinces);

    }

    @Test
    public void testCheckServiceModeNameIfExist() {
        Long count = provincesRepository
                .checkProvincesNameIfExist(1L, "Outside Patient Department");

        Assert.assertTrue(count == 0L);
    }
}
