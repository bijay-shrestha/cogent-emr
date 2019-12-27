package com.cogent.admin.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sauravi Thapa 11/4/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AssignBedRepositoryTest {

    @Autowired
    AssignBedRepository assignBedRepository;

    @Test
    public void checkIfBedExistsWithUnit(){
        Long count=assignBedRepository.checkIfBedExixts(1L,1L,1L);

        assertTrue(count != 1L);
    }

    @Test
    public void checkIfBedExists(){
        Long count=assignBedRepository.checkIfBedExixts(1L,1L,null);

        assertTrue(count != 1L);
    }
}
