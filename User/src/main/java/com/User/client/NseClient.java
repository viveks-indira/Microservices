package com.User.client;

import com.User.entity.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Component
public class NseClient {

    private final WebClient webClient;

    @Autowired
    public NseClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://192.168.46.105:9092/api/stocks").build();
    }


    public Flux<StockResponse> getAllStocks() {
        Flux<StockResponse>ans = webClient.get()
                .uri("/allStocks")
                .retrieve()
                .bodyToFlux(StockResponse.class);
        return ans;
    }
}
