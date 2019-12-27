package com.cogent.admin.repository;

import com.cogent.persistence.model.AdminApplicationModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author smriti ON 25/12/2019
 */
@Repository
public interface AdminApplicationModuleRepository extends JpaRepository<AdminApplicationModule, Long> {
}
