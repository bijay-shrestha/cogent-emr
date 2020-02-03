package com.cogent.adminservice.repository.history;

import com.cogent.persistence.history.AdminHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AdminHistoryRepository extends JpaRepository<AdminHistory, Long> {
}
