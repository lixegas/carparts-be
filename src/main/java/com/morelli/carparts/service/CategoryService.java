package com.morelli.carparts.service;

import com.morelli.carparts.mapper.CategoryMapper;
import com.morelli.carparts.model.dto.CategoryDTO;
import com.morelli.carparts.model.entity.Category;
import com.morelli.carparts.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;

    // Get all categories - ora restituisce una lista di CategoryDTO
    @Cacheable(value = "categories")
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    // Get category by ID
    @Cacheable(value = "categories", key = "#id")
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDTO(category);
    }

    // Add new category
    public CategoryDTO add(CategoryDTO categoryDTO) {
        boolean categoryExists = categoryRepository.existsByName(categoryDTO.getName());
        if (categoryExists) {
            throw new EntityExistsException("Category already exists with name: " + categoryDTO.getName());
        }
        Category category = categoryMapper.toCategory(categoryDTO);
        category.setSaveTimestamp(Instant.now());
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDTO(savedCategory);
    }

    // Update category by ID
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        categoryMapper.updateCategoryFromDTO(categoryDTO, existingCategory);
        existingCategory.setUpdateTimestamp(Instant.now());
        Category categorySaved = categoryRepository.save(existingCategory);


        return categoryMapper.toDTO(categorySaved);
    }

    // Delete category by ID
    @Caching(evict = {
            @CacheEvict(value = "categories", key = "#id"),
            @CacheEvict(value = "categories", allEntries = true)
    })
    public void delete(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        categoryRepository.delete(existingCategory);
    }
}




