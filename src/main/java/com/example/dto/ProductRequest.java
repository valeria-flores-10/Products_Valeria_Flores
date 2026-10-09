package com.example.dto;

public record ProductRequest(
        String name,
        Double price,
        int stock
) {
}
