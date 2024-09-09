package com.User.service;

import com.User.entity.StockResponse;
import reactor.core.publisher.Flux;

import java.util.List;

public interface NseClientService {
    Flux<StockResponse> getAllStocks(); // Fetch all stocks
}
