package com.cogent.admin.repository;

import com.cogent.persistence.model.ServiceDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/11/19
 */

@Repository
public interface ServiceDiscountSchemeRepository extends JpaRepository<ServiceDiscount, Long> {

    @Query("FROM ServiceDiscount sds WHERE sds.discountScheme.id=:discountSchemeId AND sds.status!='D'")
    List<ServiceDiscount> fetchByDiscountSchemeId(@Param("discountSchemeId") Long discountSchemeId);

    @Query("FROM ServiceDiscount sds WHERE sds.discountScheme.id=:discountSchemeId" +
            " AND sds.service.id=:serviceId " +
            "AND sds.status!='D'")
    ServiceDiscount fetchByDiscountSchemeAndServiceId(@Param("discountSchemeId") Long discountSchemeId,
                                                      @Param("serviceId") Long serviceId);

    @Query("FROM ServiceDiscount sds WHERE  sds.id=:id AND sds.status !='D'")
    Optional<ServiceDiscount> fetchById(@Param("id") Long id);
}
