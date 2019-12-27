package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.AppointmentModeRepositoryCustom;
import com.cogent.persistence.model.AppointmentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-10-10
 */
@Repository
public interface AppointmentModeRepository extends JpaRepository<AppointmentMode, Long>,
        AppointmentModeRepositoryCustom {

    @Query("SELECT a FROM AppointmentMode a WHERE a.status!='D' AND a.id = :id")
    Optional<AppointmentMode> findAppointmentModeById(@Param("id") Long id);

    @Query("SELECT a FROM AppointmentMode a WHERE a.status='Y' AND a.id = :id")
    Optional<AppointmentMode> fetchActiveAppointmentModeById(@Param("id") Long id);
}
