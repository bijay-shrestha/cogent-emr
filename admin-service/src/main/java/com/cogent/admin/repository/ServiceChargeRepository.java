package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ServiceChargeRepositoryCustom;
import com.cogent.persistence.model.ServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/18/19
 */

@Repository
public interface ServiceChargeRepository extends JpaRepository<ServiceCharge,Long>, ServiceChargeRepositoryCustom {

    @Query("FROM ServiceCharge fc WHERE fc.id=:id AND fc.status!='D'")
    Optional<ServiceCharge> fetchServiceChargeById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM service_charge_billing_modes WHERE service_charge_id=:id",nativeQuery = true)
    void deleteChildDataById(@Param("id") Long id);
}
