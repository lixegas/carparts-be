package com.morelli.carparts.repository;

import com.morelli.carparts.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByBarCode(Long barCode);


    @Query("SELECT p FROM Product p " +
            "WHERE (:color IS NULL OR p.color = :color) " +
            "AND (:productSize IS NULL OR p.size = :productSize) " +
            "AND (:productName IS NULL OR p.productName LIKE %:productName%)")
    Page<Product> findAllWithFilters(@Param("color") String color,
                                     @Param("productSize") String size,
                                     @Param("productName") String productName,
                                     Pageable pageable);



    @Query("SELECT p FROM Product p " +
            "WHERE p.updateTimestamp " +
            "BETWEEN :start AND :end")
    List<Product> findByUpdateTimestampBetween(@Param("start") Instant start, @Param("end") Instant end);
}

