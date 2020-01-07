package com.cogent.admin.repository;

import com.cogent.persistence.model.ServiceCharge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void fetchServiceChargeById() {
        Optional<ServiceCharge> ServiceCharge = repository.fetchServiceChargeById(1L);

        assertTrue(!ServiceCharge.equals(Optional.empty()));
    }


    @Test
    public void test() {
        String sql = "select\n" +
                "\tsc.id,\n" +
                "\tsc.price,\n" +
                "\tsc.remarks,\n" +
                "\tsc.status,\n" +
                "\tsc.service_id\n" +
                "from\n" +
                "service_charge sc\n" +
                "LEFT JOIN service_charge_billing_modes sb ON sb.service_charge_id = sc.id\n" +
                "WHERE sb.billing_modes_id = :billingModeId\n" +
                "AND sc.service_id = :service_id\n";

        List<ServiceCharge> query = testEntityManager.getEntityManager().createNativeQuery(sql, ServiceCharge.class)
                .setParameter("billingModeId", 3L)
                .setParameter("service_id", 1L)
                .getResultList();


        System.out.println(query);
    }
}
