package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.GenderRepositoryCustom;
import com.cogent.persistence.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 08/11/2019
 */
@Repository
public interface GenderRepository extends JpaRepository<Gender, Long>, GenderRepositoryCustom {

    @Query("SELECT g FROM Gender g WHERE g.status='Y' AND g.id = :id")
    Optional<Gender> fetchActiveGenderById(@Param("id") Long id);
}
