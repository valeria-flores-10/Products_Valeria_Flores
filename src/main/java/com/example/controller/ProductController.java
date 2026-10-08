package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Product;
import com.example.service.ProductService;

import java.util.List;
import java.util.Optional;


@RestController 
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service=service;

    }


    @GetMapping
    public ResponseEntity<List<Product>> fidnAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity <Product> finById(@PathVariable Long id) {
        Optional<Product> product=service.findById(id);
        if(product.isEmpty()){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @PostMapping
    public ResponseEntity<Product> savde(@RequestBody Product product){
        //TODO: process POST request
        Product newProduct = service.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product productUpdated){
        //TODO: process PUT request
        Optional<Product> product= service.update(id, productUpdated);
        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){
        boolean delete = service.delete(id);
        if(!delete){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}


