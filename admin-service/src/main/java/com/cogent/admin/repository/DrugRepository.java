package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.DrugRepositoryCustom;
import com.cogent.persistence.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long>, DrugRepositoryCustom {

    @Query(value = "SELECT d FROM Drug d WHERE  d.id=:id AND d.status !='D'")
    Optional<Drug> fetchDrugById(@Param("id") Long id);

}
