package com.User.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockResponse {
    private String symbol;
    private String name;
    private Double price;
    private Double dayLow;
    private Double dayHigh;
    private Double yearLow;
    private Double yearHigh;
    private Double marketCap;
    private String exchange;
    private Double open;
    private Double prevClose;
    // Getters and Setters Lombok

}
