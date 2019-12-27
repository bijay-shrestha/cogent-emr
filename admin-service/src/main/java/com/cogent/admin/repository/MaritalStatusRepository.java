package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.MaritalStatusRepositoryCustom;
import com.cogent.persistence.model.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long>, MaritalStatusRepositoryCustom {
    @Query("SELECT m FROM MaritalStatus m WHERE m.status!='D' AND m.id = :id")
    Optional<MaritalStatus> findMaritalStatusById(@Param("id") Long id);

    @Query("SELECT m FROM MaritalStatus m WHERE m.status='Y' AND m.id = :id")
    Optional<MaritalStatus> findActiveMaritalStatusById(@Param("id") Long id);

}
