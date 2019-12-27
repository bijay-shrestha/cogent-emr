package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ReferrerRepositoryCustom;
import com.cogent.persistence.model.Referrer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Rupak
 */
@Repository
public interface ReferrerRepository extends JpaRepository<Referrer,Long>, ReferrerRepositoryCustom {

    @Query("SELECT r FROM Referrer r WHERE r.status='Y' AND r.id = :id")
    Optional<Referrer> findReferrerStatusById(@Param("id") Long id);
}
