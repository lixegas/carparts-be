package com.morelli.carparts.repository;

import com.morelli.carparts.model.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {

    List<SaleProduct> findBySaleId(Long saleId);

    @Query("SELECT sp FROM SaleProduct sp " +
            "WHERE sp.saveTimestamp " +
            "BETWEEN :start AND :end")
    List<SaleProduct> findBySaveTimestampBetween(@Param("start") Instant start, @Param("end") Instant end);
}
