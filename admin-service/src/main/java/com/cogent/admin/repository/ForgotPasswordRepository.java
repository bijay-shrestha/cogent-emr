package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ForgotPasswordRepositoryCustom;
import com.cogent.persistence.model.ForgotPasswordVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author smriti on 2019-09-19
 */
@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordVerification, Long>, ForgotPasswordRepositoryCustom {

    @Query("SELECT f FROM ForgotPasswordVerification f WHERE f.admin.id=:id")
    ForgotPasswordVerification findByAdminId(@Param("id") Long id);
}
