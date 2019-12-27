package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.SpecializationRepositoryCustom;
import com.cogent.persistence.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-09-25
 */
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long>, SpecializationRepositoryCustom {

    @Query("SELECT s FROM Specialization s WHERE s.status!='D' AND s.id = :id")
    Optional<Specialization> findSpecializationById(@Param("id") Long id);

    @Query("SELECT s FROM Specialization s WHERE s.status='Y' AND s.id = :id")
    Optional<Specialization> findActiveSpecializationById(@Param("id") Long id);
}
