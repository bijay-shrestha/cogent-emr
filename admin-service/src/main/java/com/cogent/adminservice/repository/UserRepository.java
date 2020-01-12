package com.cogent.adminservice.repository;

import com.cogent.persistence.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Admin,Long> {
}
