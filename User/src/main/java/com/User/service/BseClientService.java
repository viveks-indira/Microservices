package com.User.service;

import com.User.entity.StockResponse;
import reactor.core.publisher.Flux;


public interface BseClientService {
    Flux<StockResponse> getAllStocks(); // Fetch all stocks
}
