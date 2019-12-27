package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.UnitRepositoryCustom;
import com.cogent.persistence.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 10/13/19
 */

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, UnitRepositoryCustom {
    @Query(value = "SELECT u FROM Unit u WHERE  u.id=:id AND u.status !='D'")
    Optional<Unit> fetchUnitById(@Param("id") Long id);
}
