package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.WardUnitRepositoryCustom;
import com.cogent.persistence.model.Ward_Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sauravi Thapa 10/20/19
 */

@Repository
public interface WardUnitRepository extends JpaRepository<Ward_Unit, Long>, WardUnitRepositoryCustom {

    @Query("SELECT count(wu.id) FROM Ward_Unit wu WHERE wu.unit.id= :unitId AND wu.ward.id= :wardId AND wu.status !='D'")
    Long fetchWardUnitCountByUnitAndWardId(@Param("unitId") Long unitId, @Param("wardId") Long wardId);

}
