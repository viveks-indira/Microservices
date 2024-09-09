package com.demo.nse.serviceImpl;

import com.demo.nse.entity.Stock;
import com.demo.nse.entity.StockResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
public class NseClientService {

    private  WebClient webClient;

    // Initialize WebClient with the base URL of the NSE service
    public NseClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:7070").build();
    }

    // Get request to fetch all stocks
    public List<StockResponse> getAllStocks() {
        List<StockResponse> list= webClient.get()
                .uri("/api/stocks/allStocks")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StockResponse>>() {})
                .block();  // Blocking for non-reactive flow, use .block() or switch to async with reactive flow

        return list;
    }

    // GET request to fetch stock by id
    public Stock getStockFromNse(Long id) {
        return webClient.get()
                .uri("/api/stocks/{id}", id)
                .retrieve()
                .bodyToMono(Stock.class)
                .block();  // Block for synchronous response
    }

    // POST request to buy stock
    public Stock buyStockFromNse(Long id, int quantity) {
        return webClient.post()
                .uri("/api/stocks/{id}/buy", id)
                .bodyValue(quantity)
                .retrieve()
                .bodyToMono(Stock.class)
                .block();  // Block for synchronous response
    }

    // POST request to sell stock
    public Stock sellStockToNse(Long id, int quantity) {
        return webClient.post()
                .uri("/api/stocks/{id}/sell", id)
                .bodyValue(quantity)
                .retrieve()
                .bodyToMono(Stock.class)
                .block();  // Block for synchronous response
    }
}
