package com.cogent.admin.repository;

import com.cogent.persistence.model.Nationality;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sauravi  on 2019-08-25
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class NationalityRepositoryTest {

    @Autowired
    private NationalityRepository nationalityRepository;

    @Test
    public void testFetchNationalityCountByName() {
        Long count = nationalityRepository.fetchNationalityCountByName("Chinese");

        assertTrue(count == 0);
    }

    @Test
    public void testFetchNationalityById() {
        Optional<Nationality> nationality = nationalityRepository.fetchNationalityById(1L);

        assertNotNull(nationality);

    }

    @Test
    public void testCheckNationalityNameIfExist() {
        Long count = nationalityRepository.checkNationalityNameIfExist(1L, "Indian");

        assertTrue(count == 0);

    }

}
