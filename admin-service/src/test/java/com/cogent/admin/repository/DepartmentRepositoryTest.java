package com.cogent.admin.repository;

import com.cogent.persistence.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;


/**
 * @author Sauravi
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testDepartmentRepository() {
        testFindDepartmentById();
        testFindActiveDepartmentById();
    }


    @Test
    public void testFindDepartmentById() {
        Optional<Department> department = departmentRepository
                .findDepartmentById(2L);

        assertNotNull(department);
    }

    @Test
    public void testFindActiveDepartmentById() {
        Optional<Department> department = departmentRepository
                .findActiveDepartmentById(2L);

        assertNotNull(department);
    }


    }

