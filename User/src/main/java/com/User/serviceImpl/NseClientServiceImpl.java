package com.User.serviceImpl;

import com.User.client.NseClient;
import com.User.client.NseClientFallback;
import com.User.entity.StockResponse;
import com.User.service.NseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class NseClientServiceImpl implements NseClientService {

    private final NseClient nseClient;
    private final NseClientFallback nseClientFallback;

    @Autowired
    public NseClientServiceImpl(NseClient nseClient, NseClientFallback nseClientFallback) {
        this.nseClient = nseClient;
        this.nseClientFallback = nseClientFallback;
    }

    // Fetch all stocks with fallback
    public Flux<StockResponse> getAllStocks() {
        Flux<StockResponse> ans= nseClient.getAllStocks()
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Flux.fromIterable(nseClientFallback.getAllStocks()); // Call the fallback
                });
        return ans;
    }
}
