package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.NationalityRepositoryCustom;
import com.cogent.persistence.model.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi  on 2019-09-19
 */

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long>, NationalityRepositoryCustom {

    @Query(value = "SELECT n FROM Nationality n WHERE  n.id=:id AND n.status !='D'")
    Optional<Nationality> fetchNationalityById(@Param("id") Long id);

    @Query(value = "SELECT n FROM Nationality n WHERE  n.id=:id AND n.status ='Y'")
    Optional<Nationality> fetchActiveNationalityById(@Param("id") Long id);

    @Query("SELECT COUNT (n.id) FROM Nationality n WHERE n.name=:name AND n.status != 'D'")
    Long fetchNationalityCountByName(@Param("name") String name);

    @Query(value = "SELECT COUNT(n.id) FROM Nationality n WHERE n.id != :id AND n.name= :name And n.status !='D'")
    Long checkNationalityNameIfExist(@Param("id") Long id, @Param("name") String name);
}
