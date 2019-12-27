package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.WardRepositoryCustom;
import com.cogent.persistence.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 10/2/19
 */

@Repository
public interface WardRepository extends JpaRepository<Ward, Long>, WardRepositoryCustom {
    @Query(value = "SELECT w FROM Ward w WHERE  w.id=:id AND w.status !='D'")
    Optional<Ward> fetchWardById(@Param("id") Long id);
}
