package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.AdminCategoryRepositoryCustom;
import com.cogent.persistence.model.AdminCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-08-11
 */
@Repository
public interface AdminCategoryRepository extends JpaRepository<AdminCategory, Long>, AdminCategoryRepositoryCustom {

    @Query("SELECT a FROM AdminCategory a WHERE a.status!='D' AND a.id = :id")
    Optional<AdminCategory> findAdminCategoryById(@Param("id") Long id);

    @Query("SELECT a FROM AdminCategory a WHERE a.status='Y' AND a.id = :id")
    Optional<AdminCategory> findActiveAdminCategoryById(@Param("id") Long id);
}
