package com.cogent.admin.repository;

import com.cogent.persistence.model.DepartmentDiscount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DepartmentDiscountRepositoryTest {
    @Autowired
    DepartmentDiscountSchemeRepository repository;

    @Test
    public void fetchByDiscountSchemeId(){
        List<DepartmentDiscount> discountScheme=repository
                .fetchByDiscountSchemeId(1L);

        assertTrue(discountScheme.size() >0);
    }
}
