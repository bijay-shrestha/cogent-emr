package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.EthnicityRepositoryCustom;
import com.cogent.persistence.model.Ethnicity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi  on 2019-08-25
 */

@Repository
public interface EthnicityRepository extends JpaRepository<Ethnicity, Long>, EthnicityRepositoryCustom {

    @Query(value = "SELECT e FROM Ethnicity e WHERE  e.id=:id AND e.status !='D'")
    Optional<Ethnicity> fetchEthnicityById(@Param("id") Long id);

    @Query(value = "SELECT e FROM Ethnicity e WHERE e.id=:id and e.status='Y'")
    Optional<Ethnicity> fetchActiveEthnicityById(@Param("id") Long id);
}
