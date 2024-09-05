package com.User.feign;

import com.User.entity.StockResponse;
import com.User.entity.UserStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "bse-service", url = "http://localhost:7070/api/stocks", fallback = BseClientFallback.class)
public interface BseClient {

    @GetMapping("/batch")
    List<StockResponse> getStocksByIds(@RequestParam List<Long> ids);

    @GetMapping("/allStocks")
    List<StockResponse> getAllStocks();
}
