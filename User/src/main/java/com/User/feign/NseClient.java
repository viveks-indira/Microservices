package com.User.feign;

import com.User.entity.StockResponse;
import com.User.entity.UserStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "nse-service", url = "http://localhost:7071/api/stocks", fallback = NseClientFallback.class)
public interface NseClient {

    @GetMapping("/batch")
    List<StockResponse> getStocksByIds(@RequestParam List<Long> ids);

    @GetMapping("/allStocks")
    List<StockResponse> getAllStocks();
}
