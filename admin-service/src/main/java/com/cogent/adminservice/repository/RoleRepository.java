package com.cogent.adminservice.repository;

import com.example.demo.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    @Query("FROM Roles r WHERE r.admin.id=:id")
    List<Roles> findRolesByAdminId(@Param("id") Long id);
}
