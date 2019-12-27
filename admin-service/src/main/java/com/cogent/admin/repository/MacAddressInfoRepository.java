package com.cogent.admin.repository;

import com.cogent.persistence.model.MacAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacAddressInfoRepository extends JpaRepository<MacAddressInfo, Long> {
}
