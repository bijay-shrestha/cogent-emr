package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.RegisteredBankRepositoryCustom;
import com.cogent.persistence.model.RegisteredBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 12/10/19
 */
@Repository
public interface RegisteredBankRepository extends JpaRepository<RegisteredBank, Long>, RegisteredBankRepositoryCustom {

    @Query("FROM RegisteredBank rb WHERE rb.id=:id AND rb.status !='D'")
    Optional<RegisteredBank> fetchRegisteredBankById(@Param("id") Long id);

    @Query("FROM RegisteredBank rb WHERE rb.id=:id AND rb.status ='Y'")
    RegisteredBank fetchActiveRegisteredBankeById(@Param("id") Long id);

    @Query("SELECT COUNT(rb.id) FROM RegisteredBank rb WHERE rb.swiftCode=:swiftCode AND rb.status!='D'")
    Long checkIfSwiftCodeExists(@Param("swiftCode") String swiftCode);

    @Query("SELECT COUNT(rb.id) FROM RegisteredBank rb WHERE rb.id!=:id AND rb.swiftCode=:swiftCode AND rb.status!='D'")
    Long checkIfSwiftCodeExistsToUpdate(@Param("id") Long id,@Param("swiftCode") String swiftCode);
}
