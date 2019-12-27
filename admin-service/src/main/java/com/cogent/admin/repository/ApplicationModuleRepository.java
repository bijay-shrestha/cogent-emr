package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ApplicationModuleRepositoryCustom;
import com.cogent.persistence.model.ApplicationModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti ON 24/12/2019
 */
@Repository
public interface ApplicationModuleRepository extends JpaRepository<ApplicationModule, Long>,
        ApplicationModuleRepositoryCustom {

    @Query("SELECT a FROM ApplicationModule a WHERE a.status!='D' AND a.id = :id")
    Optional<ApplicationModule> findApplicationModuleById(@Param("id") Long id);
}
