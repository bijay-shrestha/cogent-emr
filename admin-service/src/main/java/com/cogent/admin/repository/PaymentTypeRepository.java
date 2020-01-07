package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.PaymentTypeRepositoryCustom;
import com.cogent.persistence.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sauravi Thapa 12/11/19
 */
@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long>, PaymentTypeRepositoryCustom {

    @Query("From PaymentType p WHERE p.id=:id AND p.status='Y'")
    PaymentType fetchActivepaymentTypeById(@Param("id") Long id);
}
