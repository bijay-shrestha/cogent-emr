package com.cogent.admin.repository;

import com.cogent.persistence.model.Ward;
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
 * @author Sauravi  on 10/2/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class WardRepositoryTest {

    @Autowired
    private WardRepository wardRepository;

    @Test
    public void fetchWardById() {
        Optional<Ward> ward = wardRepository
                .fetchWardById(1L);

        assertTrue(!ward.equals(Optional.empty()));
    }
}
