package com.cogent.admin.repository;

import com.cogent.persistence.model.DoctorSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author smriti on 13/11/2019
 */
@Repository
public interface DoctorSignatureRepository extends JpaRepository<DoctorSignature, Long> {

    @Query("SELECT c FROM DoctorSignature c WHERE c.doctorId.id = :id")
    DoctorSignature findByDoctorId(@Param("id") Long id);
}
