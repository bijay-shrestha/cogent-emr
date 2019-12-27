package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.AdminConfirmationTokenRepositoryCustom;
import com.cogent.persistence.model.AdminConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-09-22
 */
@Repository
public interface AdminConfirmationTokenRepository extends JpaRepository<AdminConfirmationToken, Long>,
        AdminConfirmationTokenRepositoryCustom {

    @Query("SELECT a FROM AdminConfirmationToken a WHERE a.confirmationToken =:confirmationToken")
    Optional<AdminConfirmationToken> findAdminConfirmationTokenByToken(@Param("confirmationToken") String confirmationToken);

}
