package com.cogent.admin.repository;

import com.cogent.persistence.model.Drug;
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
public class DrugRepositoryTest {

    @Autowired
    private DrugRepository drugRepository;

    @Test
    public void fetchDrugById() {
        Optional<Drug> drug = drugRepository
                .fetchDrugById(1L);

        assertNotNull(drug);
    }

    @Test
    public void testCheckDrugNameIfExist() {
        List<Object[]> count = drugRepository
                .checkIfDrugNameAndCodeExists(1L, "TEST","TST");

        assertTrue(count.size() == 0L);
    }

}
