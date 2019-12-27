package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.FollowUpSetupRepositoryCustom;
import com.cogent.persistence.model.FollowUpSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-11-04
 */
@Repository
public interface FollowUpSetupRepository extends JpaRepository<FollowUpSetup, Long>, FollowUpSetupRepositoryCustom {

    @Query("SELECT f FROM FollowUpSetup f WHERE f.status!='D' AND f.id = :id")
    Optional<FollowUpSetup> findFollowUpSetupById(Long id);
}
