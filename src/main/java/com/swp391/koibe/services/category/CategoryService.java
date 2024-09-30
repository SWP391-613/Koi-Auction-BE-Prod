package com.swp391.koibe.services.category;

import com.swp391.koibe.dtos.CategoryDTO;
import com.swp391.koibe.exceptions.CategoryAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.CategoryNotFoundException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Category;
import com.swp391.koibe.repositories.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO category) throws DataAlreadyExistException {

        if(categoryRepository.findByName(category.name()) != null){
            throw new CategoryAlreadyExistException("Category already exists");
        }
        
        Category newCategory = Category.builder()
            .name(category.name())
            .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getById(int id) throws DataNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(int categoryId, CategoryDTO category)
        throws DataNotFoundException {

        Category existingCategory = getById(categoryId);
        existingCategory.setName(category.name());
        return categoryRepository.save(existingCategory);

    }

    @Override
    public void delete(int id) {

        categoryRepository.deleteById(id);

    }
}
