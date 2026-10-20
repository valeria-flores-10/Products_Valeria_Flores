package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.repository.ProductRepository;

@Service 
public class ProductService {

    private final ProductRepository repo;

    private final CategoryRepository repoCategory;


    public ProductService(ProductRepository repo, CategoryRepository repoCategory) {
        this.repo=repo;
        this.repoCategory=repoCategory;
    }

    public List<Product> findAll(){
        return repo.findAll();
    }

    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }

    public Product  save(Product product, Long id){
            Category category = repoCategory.findById(id).orElseThrow();
            product.setCategory(category);
            return repo.save(product);
    }

    public Optional<Product> update(long id, Product newProduct, Long categoryId){
        Product productBd=repo.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not found with id " + id)
        );

        Category category = repoCategory.findById(categoryId).orElseThrow(
                ()->
                        new ResourceNotFoundException("Category not found ehit id" + categoryId)
        );
        //Product productBd=optional.get();
        productBd.setName(newProduct.getName() + "");
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());
        return Optional.of(repo.save(productBd));
    }

    public boolean delete(long id){
        if(!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }

}

