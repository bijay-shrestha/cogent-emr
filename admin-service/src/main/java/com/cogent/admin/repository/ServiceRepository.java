package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.ServiceRepositoryCustom;
import com.cogent.persistence.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 11/18/19
 */
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>, ServiceRepositoryCustom {

    @Query("FROM Service s WHERE s.id=:id AND s.status!='D'")
    Optional<Service> fetchServiceById(@Param("id") Long id);

    @Query("FROM Service s WHERE s.id=:id AND s.status='Y'")
    Optional<Service> fetchActiveServiceById(@Param("id") Long id);
}
