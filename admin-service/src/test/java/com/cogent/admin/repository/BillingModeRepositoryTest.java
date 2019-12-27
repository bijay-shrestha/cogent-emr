package com.cogent.admin.repository;

import com.cogent.persistence.model.BillingMode;
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
public class BillingModeRepositoryTest {

    @Autowired
    BillingModeRepository repository;

    @Test
    public void fetchBedById() {
        Optional<BillingMode> billingMode = repository.fetchBillingModeById(1L);

        assertTrue(!billingMode.equals(Optional.empty()));
    }

    @Test
    public void fetchActiveBillingModeById() {
        BillingMode billingMode = repository.fetchActiveBillingModeById(1L);

        assertTrue(!billingMode.equals(null));
    }

}
