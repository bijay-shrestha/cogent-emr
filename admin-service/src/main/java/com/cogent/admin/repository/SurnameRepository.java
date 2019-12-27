package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.SurnameRepositoryCustom;
import com.cogent.persistence.model.Surname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author smriti on 2019-09-12
 */
@Repository
public interface SurnameRepository extends JpaRepository<Surname, Long>, SurnameRepositoryCustom {

    @Query("SELECT s FROM Surname s WHERE s.status!='D' AND s.id = :id")
    Optional<Surname> findSurnameById(@Param("id") Long id);
}
