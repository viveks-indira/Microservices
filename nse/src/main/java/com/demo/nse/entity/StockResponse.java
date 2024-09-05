package com.demo.nse.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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

}
