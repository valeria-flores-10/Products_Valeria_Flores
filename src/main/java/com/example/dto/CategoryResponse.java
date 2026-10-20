package com.example.dto;

public record CategoryResponse(
        Long id,
        String name
) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}
