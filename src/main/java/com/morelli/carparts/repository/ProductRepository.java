package com.morelli.carparts.repository;

import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductByBarCode(Long barCode);
}
