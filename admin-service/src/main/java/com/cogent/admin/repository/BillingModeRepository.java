package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.BillingModeRepositoryCustom;
import com.cogent.persistence.model.BillingMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 12/4/19
 */
@Repository
public interface BillingModeRepository extends JpaRepository<BillingMode, Long>, BillingModeRepositoryCustom {

    @Query("FROM BillingMode bm WHERE  bm.id=:id AND bm.status !='D'")
    Optional<BillingMode> fetchBillingModeById(@Param("id") Long id);

    @Query("FROM BillingMode bm WHERE bm.id=:id AND bm.status ='Y'")
    BillingMode fetchActiveBillingModeById(@Param("id") Long id);


}
