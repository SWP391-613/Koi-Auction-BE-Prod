package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.CategoryDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.responses.CategoryResponse;
import com.swp391.koibe.services.category.ICategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final ICategoryService categoryService;
    private final LocalizationUtils localizationUtils;

    @GetMapping("")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse response = CategoryResponse.builder()
            .message(localizationUtils.getLocalizedMessage("category.get_all_successfully"))
            .listData(categoryService.getAllCategories())
            .build();
        try {
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage("category.get_all_failed"));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {

        try {
            CategoryResponse response = CategoryResponse.builder()
                .message(localizationUtils.getLocalizedMessage("category.get_by_id_successfully"))
                .singleData(categoryService.getById(id))
                .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage("category.get_by_id_failed"));
        }
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponse> createCategory(
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result) {


        try {
            CategoryResponse response = CategoryResponse.builder()
                .message(localizationUtils.getLocalizedMessage(
                    "category.create_category.create_successfully"))
                .singleData(categoryService.createCategory(categoryDTO))
                .build();

            categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new RuntimeException(
                localizationUtils.
                    getLocalizedMessage("category.create_category.create_failed"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
        @PathVariable int id,
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        try {
            CategoryResponse response = CategoryResponse.builder()
                .message(localizationUtils.getLocalizedMessage(
                    "category.update_category.update_successfully"))
                .singleData(categoryService.update(id, categoryDTO))
                .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new RuntimeException(
                localizationUtils.
                    getLocalizedMessage("category.update_category.update_failed"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable int id) {
        try {
           CategoryResponse response = CategoryResponse.builder()
                .message(localizationUtils.getLocalizedMessage(
                    "category.delete_category.delete_successfully", id))
                .build();

            categoryService.delete(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(
                localizationUtils.
                    getLocalizedMessage("category.delete_category.delete_failed"));
        }
    }


}
