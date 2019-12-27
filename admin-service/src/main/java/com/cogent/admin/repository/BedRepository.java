package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.BedRepositoryCustom;
import com.cogent.persistence.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 11/1/19
 */

@Repository
public interface BedRepository extends JpaRepository<Bed, Long>, BedRepositoryCustom {
    @Query(value = "SELECT b FROM Bed b WHERE  b.id=:id AND b.status !='D'")
    Optional<Bed> fetchBedById(@Param("id") Long id);

    @Query(value = "SELECT b FROM Bed b WHERE  b.id=:id AND b.status ='Y'")
    Bed fetchActiveBedById(@Param("id") Long id);
}
