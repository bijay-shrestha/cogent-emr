package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.DistrictRepositoryCustom;
import com.cogent.persistence.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>, DistrictRepositoryCustom {

    @Query(value = "SELECT d FROM District d WHERE d.id=:id and d.status='Y'")
    Optional<District> findActiveDistrictById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(d.id) FROM District d WHERE d.id != :id AND d.name= :name And d.status !='D'")
    Long checkDistrictNameIfExist(@Param("id") Long id, @Param("name") String name);

}
