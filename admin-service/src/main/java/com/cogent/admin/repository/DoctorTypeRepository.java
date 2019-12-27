package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.DoctorTypeRepositoryCustom;
import com.cogent.persistence.model.DoctorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 08/11/2019
 */
@Repository
public interface DoctorTypeRepository extends JpaRepository<DoctorType, Long>, DoctorTypeRepositoryCustom {

    @Query("SELECT c FROM DoctorType c WHERE c.status='Y' AND c.id = :id")
    Optional<DoctorType> fetchActiveDoctorTypeById(@Param("id") Long id);
}
