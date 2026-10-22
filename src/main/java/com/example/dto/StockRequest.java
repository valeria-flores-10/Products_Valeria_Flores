package com.example.dto;

public class StockRequest {
    String word;
    Integer quantity;

    public StockRequest() {
    }
    public StockRequest(String word, Integer quantity) {
        this.word = word;
        this.quantity = quantity;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
