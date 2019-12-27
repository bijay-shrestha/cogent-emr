package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.AssignBedRepositoryCustom;
import com.cogent.persistence.model.AssignBed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sauravi Thapa 11/8/19
 */

@Repository
public interface AssignBedRepository extends JpaRepository<AssignBed, Long>, AssignBedRepositoryCustom {

}
