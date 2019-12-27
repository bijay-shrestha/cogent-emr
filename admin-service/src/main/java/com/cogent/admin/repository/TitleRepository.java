package com.cogent.admin.repository;

import com.cogent.admin.repository.custom.TitleRepositoryCustom;
import com.cogent.persistence.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long>, TitleRepositoryCustom {
    @Query("SELECT t FROM Title t WHERE t.status!='D' AND t.id = :id")
    Optional<Title> findTitleById(@Param("id") Long id);

    @Query("SELECT t FROM Title t WHERE t.status='Y' AND t.id = :id")
    Optional<Title> findActiveTitleById(@Param("id") Long id);


}
