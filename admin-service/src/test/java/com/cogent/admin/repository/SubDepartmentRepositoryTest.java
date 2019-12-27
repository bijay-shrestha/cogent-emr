package com.cogent.admin.repository;

import com.cogent.persistence.model.SubDepartment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SubDepartmentRepositoryTest {
    @Autowired
    SubDepartmentRepository subDepartmentRepository;

    @Test
    public void testSubDepartmentRepository() {
        findSubDepartmentById();
        findSubDepartmentByDepartmentId();
    }

    @Test
    public void findSubDepartmentById() {
        Optional<SubDepartment> subDepartment = subDepartmentRepository
                .findById(1L);

        assertNotNull(subDepartment);
    }

    @Test
    public void findSubDepartmentByDepartmentId() {
        List<Long> id = subDepartmentRepository
                .fetchSubDepartmentIdByDepartmentId(1L);

        assertNotNull(id);
    }



}
