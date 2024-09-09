package com.User.serviceImpl;

import com.User.entity.StockResponse;
import com.User.service.BseClientService;
import com.User.service.NseClientService;
import com.User.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private BseClientService bseClientService; // Inject Feign Client
    @Autowired
    private NseClientService nseClientService; // Inject Feign Client

    @Override
    public List<StockResponse> getAllStocks() {
        // Fetch stock details from BSE and handle errors by resuming with an empty flux
        Flux<StockResponse> bseStocksFlux = bseClientService.getAllStocks()
                .doOnNext(stock -> System.out.println("Fetched BSE stock: " + stock))
                .onErrorResume(e -> {
                    System.out.println("Error fetching BSE stocks: " + e.getMessage());
                    return Flux.empty(); // Resume with empty flux in case of error
                });

        // Fetch stock details from NSE and handle errors by resuming with an empty flux
        Flux<StockResponse> nseStocksFlux = nseClientService.getAllStocks()
                .doOnNext(stock -> System.out.println("Fetched NSE stock: " + stock))
                .onErrorResume(e -> {
                    System.out.println("Error fetching NSE stocks: " + e.getMessage());
                    return Flux.empty(); // Resume with empty flux in case of error
                });

        // Combine both fluxes (BSE and NSE) and collect into a list
        List<StockResponse> allStocks = Flux.merge(bseStocksFlux, nseStocksFlux)
                .collectList() // Collect the flux into a List
                .block(); // Block to return the result synchronously (this is optional in a reactive context)

        // If both services failed and no data is available, throw an exception
        if (allStocks == null || allStocks.isEmpty()) {
            throw new RuntimeException("Both NSE and BSE services are not available.");
        }

        return allStocks;
    }
}
