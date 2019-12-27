package com.cogent.admin.repository;

import com.cogent.persistence.model.ServiceCharge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Sauravi Thapa 11/18/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ServiceChargeRepositoryTest {

    @Autowired
    ServiceChargeRepository repository;

    @Test
    public void fetchServiceChargeById(){
        Optional<ServiceCharge> ServiceCharge=repository.fetchServiceChargeById(1L);

        assertTrue(!ServiceCharge.equals(Optional.empty()));
    }
}
