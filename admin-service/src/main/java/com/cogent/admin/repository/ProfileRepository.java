package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ProfileRepositoryCustom;
import com.cogent.persistence.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 7/2/19
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom {

    @Query("SELECT p FROM Profile p WHERE p.status!='D' AND p.id = :id")
    Optional<Profile> findProfileById(@Param("id") Long id);

    @Query("SELECT p FROM Profile p WHERE p.status!='D' AND p.subDepartment.id = :id")
    Profile findProfileBySubDepartmentId(@Param("id") Long id);

    @Query("SELECT p FROM Profile p WHERE p.status='Y' AND p.id = :id")
    Optional<Profile> findActiveProfileById(@Param("id") Long id);
}
