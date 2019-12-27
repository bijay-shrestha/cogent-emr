package com.cogent.admin.repository;

import com.cogent.persistence.model.RegisteredBank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * @author Sauravi Thapa 11/1/19
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class RegisteredBankRepositoryTest {

    @Autowired
    RegisteredBankRepository repository;

    @Test
    public void fetchBedById() {
        Optional<RegisteredBank> registeredBank = repository.fetchRegisteredBankById(1L);

        assertTrue(!registeredBank.equals(Optional.empty()));
    }

    @Test
    public void fetchActiveBillingModeById() {
        RegisteredBank registeredBank = repository.fetchActiveRegisteredBankeById(1L);

        assertTrue(!registeredBank.equals(null));
    }

}
