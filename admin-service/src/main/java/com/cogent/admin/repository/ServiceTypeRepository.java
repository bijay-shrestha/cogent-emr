package com.cogent.admin.repository;

import com.cogent.persistence.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi Thapa 11/18/19
 */

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType,Long> {

    @Query(value = "SELECT st FROM ServiceType st WHERE st.id=:id AND st.status ='Y'")
    Optional<ServiceType> fetchActiveServiceTypeById(@Param("id") Long id);
}
