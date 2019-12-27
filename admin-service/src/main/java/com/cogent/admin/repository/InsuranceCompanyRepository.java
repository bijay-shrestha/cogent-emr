package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.InsuranceCompanyRepositoryCustom;
import com.cogent.persistence.model.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sauravi  on 2019-09-20
 */

@Repository
public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Long>,
        InsuranceCompanyRepositoryCustom {

    @Query(value = "SELECT ic FROM InsuranceCompany ic WHERE  ic.id=:id AND ic.status !='D'")
    Optional<InsuranceCompany> fetchInsuranceCompanyById(@Param("id") Long id);

    @Query(value = "SELECT ic FROM InsuranceCompany ic WHERE  ic.id=:id AND ic.status ='Y'")
    Optional<InsuranceCompany> fetchActiveInsuranceCompanyById(@Param("id") Long id);

    @Query("SELECT COUNT (ic.id) FROM InsuranceCompany ic WHERE ic.name=:name AND ic.status != 'D'")
    Long fetchInsuranceCompanyCountByName(@Param("name") String name);

    @Query(value = "SELECT COUNT(ic.id) FROM InsuranceCompany ic WHERE ic.id != :id AND ic.name= :name " +
            "And ic.status !='D'")
    Long checkInsuranceCompanyNameIfExist(@Param("id") Long id, @Param("name") String name);
}
