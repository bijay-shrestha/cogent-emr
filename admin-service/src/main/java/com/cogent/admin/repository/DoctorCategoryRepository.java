package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.DoctorCategoryRepositoryCustom;
import com.cogent.persistence.model.DoctorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 11/21/19
 */

@Repository
public interface DoctorCategoryRepository extends JpaRepository<DoctorCategory,Long>, DoctorCategoryRepositoryCustom {

    @Query(value = "SELECT d FROM DoctorCategory d WHERE  d.id=:id AND d.status !='D'")
    Optional<DoctorCategory> fetchDoctorCategoryById(@Param("id") Long id);
}
