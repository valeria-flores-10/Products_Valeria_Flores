package com.example.controller;

import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Product;
import com.example.service.ProductService;

import java.util.ArrayList;
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
    public ResponseEntity<List<ProductResponse>> fidnAll() {
        List<ProductResponse> list = new ArrayList<>();

        for (Product elem : service.findAll()) {
            list.add(toResponse(elem));
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity <ProductResponse> finById(@PathVariable Long id) {
        Optional<Product> product=service.findById(id);
        if(product.isEmpty()){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(product.get()));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> savde(@Valid  @RequestBody Product product){
        //TODO: process POST request
        Product newProduct = service.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@Valid @PathVariable Long id, @RequestBody ProductRequest productUpdated){
        //TODO: process PUT request
        Optional<Product> product= service.update(id, toEntity(productUpdated));
        if(product.isPresent()){
            return ResponseEntity.ok(toResponse(product.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean delete = service.delete(id);
        if(!delete){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    private Product toEntity(ProductRequest product){
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }
}


