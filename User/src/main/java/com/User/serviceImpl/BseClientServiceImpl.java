package com.User.serviceImpl;

import com.User.client.BseClient;
import com.User.client.BseClientFallback;
import com.User.entity.StockResponse;
import com.User.service.BseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class BseClientServiceImpl implements BseClientService {

    private final BseClient bseClient;
    private final BseClientFallback bseClientFallback;

    @Autowired
    public BseClientServiceImpl(BseClient bseClient, BseClientFallback bseClientFallback) {
        this.bseClient = bseClient;
        this.bseClientFallback = bseClientFallback;
    }

    // Fetch all stocks with fallback
    public Flux<StockResponse> getAllStocks() {
        Flux<StockResponse> ans= bseClient.getAllStocks()
                .onErrorResume(e -> {
                    // Log the error (optional)
                    return Flux.fromIterable(bseClientFallback.getAllStocks()); // Call the fallback
                });
        return ans;
    }
}
