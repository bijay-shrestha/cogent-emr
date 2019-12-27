package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.AppointmentTypeRepositoryCustom;
import com.cogent.persistence.model.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-09-26
 */
@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long>,
        AppointmentTypeRepositoryCustom {

    @Query("SELECT a FROM AppointmentType a WHERE a.status!='D' AND a.id = :id")
    Optional<AppointmentType> findAppointmentTypeById(@Param("id") Long id);

    @Query("SELECT a FROM AppointmentType a WHERE a.status='Y' AND a.id = :id")
    Optional<AppointmentType> fetchActiveAppointmentTypeById(@Param("id") Long id);
}
