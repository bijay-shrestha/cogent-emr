package com.cogent.admin.repository;

import com.cogent.persistence.model.InsuranceCompany;
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
 * @author Sauravi  on 2019-09-20
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class InsuranceCompanyRepositoryTest {

    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    @Test
    public void testFetchInsuranceCompanyCountByName() {
        Long count = insuranceCompanyRepository.fetchInsuranceCompanyCountByName("Test");

        assertTrue(count == 0);
    }

    @Test
    public void testFetchInsuranceCompanyById() {
        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.fetchInsuranceCompanyById(1L);

        assertNotNull(insuranceCompany);

    }

    @Test
    public void testCheckInsuranceCompanyNameIfExist() {
        Long count = insuranceCompanyRepository.checkInsuranceCompanyNameIfExist(1L, "Test");

        assertTrue(count == 0);

    }

}
