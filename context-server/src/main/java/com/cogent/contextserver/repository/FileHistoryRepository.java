package com.cogent.contextserver.repository;

import com.cogent.contextserver.model.FileHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FileHistoryRepository extends JpaRepository<FileHistory, Integer> {
}
