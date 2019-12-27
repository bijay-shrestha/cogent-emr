package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ProvincesRepositoryCustom;
import com.cogent.persistence.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi  on 2019-08-25
 */

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, Long>, ProvincesRepositoryCustom {

    @Query(value = "SELECT p FROM Provinces p WHERE  p.id=:id AND p.status !='D'")
    Optional<Provinces> fetchprovincesById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(p.id) FROM Provinces p WHERE p.id != :id AND p.name= :name And p.status !='D'")
    Long checkProvincesNameIfExist(@Param("id") Long id, @Param("name") String name);

    @Query(value = "SELECT p FROM Provinces p WHERE p.id=:id and p.status='Y'")
    Optional<Provinces> findActiveProvincesById(@Param("id") Long id);

    @Query(value = "SELECT p FROM Provinces p WHERE  p.id=:id AND p.status !='D'")
    Provinces findprovincesById(@Param("id") Long id);

}
