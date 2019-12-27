package com.cogent.admin.repository;

import com.cogent.persistence.model.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author smriti on 2019-09-29
 */
@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long> {
}
