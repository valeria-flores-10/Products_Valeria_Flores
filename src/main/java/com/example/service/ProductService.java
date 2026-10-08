package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.repository.ProductRepository;

@Service 
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo){
        this.repo=repo;
    }

    public List<Product> findAll(){
        return repo.findAll();
    }

    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }

    public Product  save(Product product){
        return repo.save(product);
    }

    public Optional<Product> update(long id, Product newProduct){
        Optional<Product> optional=repo.findById(id);
        Product productBd=optional.get();
        productBd.setName(newProduct.getName());
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

