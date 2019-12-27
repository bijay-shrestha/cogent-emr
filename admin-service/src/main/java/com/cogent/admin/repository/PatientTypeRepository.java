package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.PatientTypeRepositoryCustom;
import com.cogent.persistence.model.PatientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author smriti on 2019-09-26
 */
public interface PatientTypeRepository extends JpaRepository<PatientType, Long>,
        PatientTypeRepositoryCustom {

    @Query("SELECT p FROM PatientType p WHERE p.status!='D' AND p.id = :id")
    Optional<PatientType> findPatientTypeById(@Param("id") Long id);

    @Query("SELECT p FROM PatientType p WHERE p.status='Y' AND p.id = :id")
    Optional<PatientType> findActivePatientTypeById(@Param("id") Long id);
}

