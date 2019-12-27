package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.CategoryRepositoryCustom;
import com.cogent.persistence.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 10/24/19
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    @Query(value = "SELECT c FROM Category c WHERE  c.id=:id AND c.status !='D'")
    Optional<Category> fetchCategoryById(@Param("id") Long id);

    @Query(value = "SELECT c FROM Category c WHERE c.id=:id and c.status='Y'")
    Optional<Category> fetchActiveCategoryById(@Param("id") Long id);
}
