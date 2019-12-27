package com.cogent.admin.repository;

import com.cogent.persistence.model.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author smriti on 17/11/2019
 */
@Repository
public interface AdminProfileRepository extends JpaRepository<AdminProfile, Long> {
}
