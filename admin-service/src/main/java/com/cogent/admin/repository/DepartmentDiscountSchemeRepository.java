package com.cogent.admin.repository;

import com.cogent.persistence.model.DepartmentDiscount;
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
public interface DepartmentDiscountSchemeRepository extends JpaRepository<DepartmentDiscount, Long> {

    @Query("FROM DepartmentDiscount dds WHERE  dds.discountScheme.id=:discountSchemeId AND dds.status !='D'")
    List<DepartmentDiscount> fetchByDiscountSchemeId(@Param("discountSchemeId") Long discountSchemeId);

    @Query("FROM DepartmentDiscount dds WHERE dds.department.id=:departmentId AND " +
            "dds.discountScheme.id=:discountSchemeId" +
            " AND dds.status !='D'")
    DepartmentDiscount fetchByDiscountSchemeAndDepartmentId(@Param("departmentId") Long departmentId,
                                                            @Param("discountSchemeId") Long discountSchemeId);

    @Query("FROM DepartmentDiscount dds WHERE  dds.id=:id AND dds.status !='D'")
    Optional<DepartmentDiscount> fetchById(@Param("id") Long id);
}
