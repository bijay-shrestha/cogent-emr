package com.cogent.admin.repository;

import com.cogent.persistence.model.Ethnicity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sauravi  on 2019-08-25
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class EthnicityRepositoryTest {

    @Autowired
    private EthnicityRepository ethnicityRepository;

    @Test
    public void fetchEthnicityById() {
        Optional<Ethnicity> ethnicity = ethnicityRepository
                .fetchEthnicityById(1L);

        assertTrue(!ethnicity.equals(Optional.empty()));
    }



    @Test
    public void fetchActiveEthnicityById() {
        Optional<Ethnicity> ethnicity = ethnicityRepository
                .fetchActiveEthnicityById(1L);

        assertTrue(!ethnicity.equals(Optional.empty()));
    }
}
