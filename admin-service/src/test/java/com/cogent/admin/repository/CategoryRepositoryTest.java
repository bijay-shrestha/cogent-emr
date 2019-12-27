package com.cogent.admin.repository;

import com.cogent.persistence.model.Category;
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
 * @author Sauravi Thapa 10/24/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void fetchEthnicityById() {
        Optional<Category> category = categoryRepository
                .fetchCategoryById(1L);

        assertTrue(!category.equals(Optional.empty()));
    }



    @Test
    public void fetchActiveEthnicityById() {
        Optional<Category> category = categoryRepository
                .fetchActiveCategoryById(1L);

        assertTrue(!category.equals(Optional.empty()));
    }

}
