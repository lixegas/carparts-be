package com.morelli.carparts.controller;


import com.morelli.carparts.model.dto.CategoryDTO;
import com.morelli.carparts.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/storage/category")
@AllArgsConstructor
public class CategoryController {

     private final CategoryService categoryService;


     // GET all categories
     // FUNZIONA
     @GetMapping("/")
     public ResponseEntity<List<CategoryDTO>> getAllCategories() {
          List<CategoryDTO> categoryDTOs = categoryService.getAll();
          return ResponseEntity.ok(categoryDTOs);
     }

     // GET category by ID
     @GetMapping("/{id}")
     // FUNZIONA
     public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
          CategoryDTO categoryDTO = categoryService.getCategoryById(id);
          return ResponseEntity.ok(categoryDTO);
     }

     // POST create new category
     // FUNZIONA
     @PostMapping("/")
     public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
          CategoryDTO createdCategoryDTO = categoryService.add(categoryDTO);
          return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryDTO);
     }

     // PUT update existing category by ID
     // FUNZIONA
     @PutMapping("/{id}")
     public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
          CategoryDTO updatedCategoryDTO = categoryService.update(id, categoryDTO);
          return ResponseEntity.ok(updatedCategoryDTO);
     }

     // DELETE category by ID
     // FUNZIONA
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
          categoryService.delete(id);
          return ResponseEntity.noContent().build();
     }
}
