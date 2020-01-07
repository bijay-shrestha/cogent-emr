package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.DiscountSchemeRepositoryCustom;
import com.cogent.persistence.model.DiscountScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 11/11/19
 */

@Repository
public interface DiscountSchemeRepository extends JpaRepository<DiscountScheme, Long>, DiscountSchemeRepositoryCustom {

    @Query("FROM DiscountScheme ds WHERE  ds.id=:id AND ds.status !='D'")
    Optional<DiscountScheme> fetchDiscountSchemeById(@Param("id") Long id);

    @Query("FROM DiscountScheme ds WHERE  ds.id=:id AND ds.status ='Y'")
    DiscountScheme fetchActiveDiscountSchemeById(@Param("id") Long id);
}
