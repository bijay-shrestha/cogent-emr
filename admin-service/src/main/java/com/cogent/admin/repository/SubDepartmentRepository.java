package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.SubDepartmentRepositoryCustom;
import com.cogent.persistence.model.SubDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi
 */

@Repository
public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Long>, SubDepartmentRepositoryCustom {

    @Query(value = "SELECT s FROM SubDepartment s WHERE s.id=:id AND s.status='Y'")
    Optional<SubDepartment> findActiveSubDepartmentById(@Param("id") Long id);

    @Query(value = "SELECT s.id FROM SubDepartment s WHERE s.department.id= :id AND s.status !='D'")
    List<Long> fetchSubDepartmentIdByDepartmentId(@Param("id") Long id);

}
