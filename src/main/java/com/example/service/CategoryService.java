package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repo.findById(id);
    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public Optional<Category> update(long id, Category newCategory) {
        Category categoryDb = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id " + id)
        );

        categoryDb.setName(newCategory.getName());

        return Optional.of(repo.save(categoryDb));
    }

    public boolean delete(long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
