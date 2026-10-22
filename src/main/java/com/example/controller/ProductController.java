package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.dto.DescuentoRequest;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.StockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import com.example.model.Product;
import com.example.service.ProductService;

import jakarta.validation.Valid;




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
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product = service.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not found " + id)
        );
        return ResponseEntity.ok(toResponse(product));
    }
    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {
        Product newProduct = service.save(toEntity(product),product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }
    @PutMapping("/{id}")
    public  ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid  @RequestBody ProductRequest productUpdate) {
        Product product= service.update(id,
                toEntity(productUpdate),
                productUpdate.categoryId()
        );
        return ResponseEntity.ok(toResponse(product));
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updatePrice(@PathVariable Long id, @RequestBody DescuentoRequest request) {
        Product product = service.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not found " + id)
        );
        product.getStock();
        if (product.getStock()<=0){
           throw  new  StockException("El producto tiene 0 unidades");
        }
        product.getPrice();
        request.getDescuento();

        double nuevoPrecio = product.getPrice() - (product.getPrice() * (request.getDescuento() / 100.0));
        product.setPrice(nuevoPrecio);
        service.update(id,product,product.getCategory().getId());
        //ProductResponse precioDescuento = toResponse(product);
        return  ResponseEntity.ok(toResponse(product));
    }


    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getStock(),"",0L);
    }

    private Product toEntity(ProductRequest product){
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }
}