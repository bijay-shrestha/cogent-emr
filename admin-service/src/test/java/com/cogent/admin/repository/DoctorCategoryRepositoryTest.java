package com.cogent.admin.repository;

import com.cogent.persistence.model.DoctorCategory;
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
public class DoctorCategoryRepositoryTest {

    @Autowired
    private DoctorCategoryRepository repository;

    @Test
    public void fetchDrugById() {
        Optional<DoctorCategory> drug = repository
                .fetchDoctorCategoryById(1L);

        assertNotNull(drug);
    }



}
