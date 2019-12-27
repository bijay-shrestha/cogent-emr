package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ReligionRepositoryCustom;
import com.cogent.persistence.model.Religion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReligionRepository extends JpaRepository<Religion, Long>, ReligionRepositoryCustom {

    @Query("SELECT r FROM Religion r WHERE r.status!='D' AND r.id = :id")
    Optional<Religion> findReligionById(@Param("id") Long id);

    @Query("SELECT r FROM Religion r WHERE r.status='Y' AND r.id = :id")
    Optional<Religion> findActiveReligionById(@Param("id") Long id);

}
