package com.example.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    String name;



    public CategoryRequest(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
