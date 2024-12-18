package com.morelli.carparts.repository;

import com.morelli.carparts.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :start AND :end")
    List<Sale> findBySaleDateBetween(@Param("start") Instant start, @Param("end") Instant end);
}
