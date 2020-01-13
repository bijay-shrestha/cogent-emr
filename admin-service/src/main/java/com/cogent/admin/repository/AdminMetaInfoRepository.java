package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.AdminMetaInfoRepositoryCustom;
import com.cogent.persistence.model.AdminMetaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author smriti on 2019-08-27
 */
@Repository
public interface AdminMetaInfoRepository extends JpaRepository<AdminMetaInfo, Long>,
        AdminMetaInfoRepositoryCustom {

    @Query("SELECT a FROM AdminMetaInfo a WHERE a.admin.id = :id")
    AdminMetaInfo findAdminMetaInfoByAdminId(@Param("id") Long id);
}
