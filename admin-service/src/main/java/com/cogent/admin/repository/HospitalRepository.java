package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.HospitalRepositoryCustom;
import com.cogent.persistence.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Rupak
 */
public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalRepositoryCustom {
    @Query("SELECT h FROM Hospital h WHERE h.status!='D' AND h.id = :id")
    Optional<Hospital> findHospitalStatusById(@Param("id") Long id);

    @Query("SELECT h FROM Hospital h WHERE h.status!='D' AND h.id = :id")
    Optional<Hospital> findHospitalById(Long id);

    @Query("SELECT h FROM Hospital h WHERE h.status='Y' AND h.id = :id")
    Optional<Hospital> findActiveHospitalById(Long id);

    @Query("FROM Hospital h WHERE h.status='Y' AND h.id = :id")
    Hospital fetchActiveHospitalById(Long id);
}
