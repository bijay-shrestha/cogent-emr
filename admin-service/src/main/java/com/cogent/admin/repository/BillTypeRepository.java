package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.BillTypeRepositoryCustom;
import com.cogent.persistence.model.BillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-10-22
 */
@Repository
public interface BillTypeRepository extends JpaRepository<BillType, Long>, BillTypeRepositoryCustom {

    @Query("SELECT b FROM BillType b WHERE b.status='Y' AND b.id = :id")
    Optional<BillType> fetchActiveBillTypeById(@Param("id") Long id);
}
