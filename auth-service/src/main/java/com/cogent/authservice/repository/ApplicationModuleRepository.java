package com.cogent.authservice.repository;

import com.cogent.persistence.model.ApplicationModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationModuleRepository extends JpaRepository<ApplicationModule, Long> {

    @Query("SELECT a FROM ApplicationModule a LEFT JOIN AdminApplicationModule am ON a.id = am.adminId" +
            " WHERE am.adminId=:id")
    List<ApplicationModule> findApplicationModuleByAdminId(@Param("id") Long id);
}
