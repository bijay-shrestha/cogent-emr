package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.MunicipalityRepositoryCustom;
import com.cogent.persistence.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-09-15
 */
@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long>, MunicipalityRepositoryCustom {

    @Query("SELECT m FROM Municipality m WHERE m.status!='D' AND m.id = :id")
    Optional<Municipality> findMunicipalityById(@Param("id") Long id);
}
