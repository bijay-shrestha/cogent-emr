package com.cogent.admin.repository;

import com.cogent.persistence.model.DoctorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author smriti on 2019-10-18
 */
@Repository
public interface DoctorDepartmentRepository extends JpaRepository<DoctorDepartment, Long> {
}
