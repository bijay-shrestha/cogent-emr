package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.WeekDaysRepositoryCustom;
import com.cogent.persistence.model.WeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 25/11/2019
 */
@Repository
public interface WeekDaysRepository extends JpaRepository<WeekDays, Long>, WeekDaysRepositoryCustom {

    @Query("SELECT w FROM WeekDays w WHERE w.status='Y' AND w.id = :id")
    Optional<WeekDays> fetchActiveWeekDaysById(@Param("id") Long id);
}
