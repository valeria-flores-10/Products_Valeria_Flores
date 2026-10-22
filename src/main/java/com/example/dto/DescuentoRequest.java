package com.example.dto;

import jakarta.validation.constraints.*;

public class DescuentoRequest {
    @NotNull(message = "El descuento no puede ser nulo")
    @Min(1)
    @Max(100)
    double descuento;

    public DescuentoRequest() {
    }

    public DescuentoRequest(double descuento) {
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
