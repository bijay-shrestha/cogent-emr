package com.cogent.admin.repository;

import com.cogent.persistence.model.DiscountScheme;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DiscountSchemeRepositoryTest {
    @Autowired
    DiscountSchemeRepository repository;

    @Test
    public void fetchDiscountSchemeById(){
        Optional<DiscountScheme> discountScheme=repository.fetchDiscountSchemeById(1L);

        assertTrue(!discountScheme.equals(Optional.empty()));
    }




}
