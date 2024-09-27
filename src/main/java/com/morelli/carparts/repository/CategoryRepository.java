package com.morelli.carparts.repository;

import com.morelli.carparts.model.entity.Category;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
