package com.cogent.contextserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cogent.contextserver.model.File;


@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
}
