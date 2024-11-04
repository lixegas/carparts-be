package com.morelli.carparts.repository;

import com.morelli.carparts.model.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    @Query("SELECT c FROM Category c WHERE c.saveTimestamp BETWEEN :start AND :end")
    List<Category> findBySaveTimestampBetween(@Param("start") Instant start, @Param("end") Instant end);

    @Query("SELECT c FROM Category c WHERE c.updateTimestamp BETWEEN :start AND :end")
    List<Category> findByUpdateTimestampBetween(@Param("start") Instant start, @Param("end") Instant end);

}
