package com.User.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserStockResponse {
    private String symbol;
    private String name;
    private Double price;
    private Integer quantity;
    private String exchange;

    public UserStockResponse(String symbol, String name, Double price, Integer quantity, String exchange) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.exchange = exchange;
    }
}
