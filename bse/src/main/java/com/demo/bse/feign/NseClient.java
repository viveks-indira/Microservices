package com.demo.bse.feign;

import com.demo.bse.entity.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nse", url = "http://localhost:7071")
public interface NseClient {

    @GetMapping("/api/stocks/{id}")
    Stock getStockFromNse(@PathVariable("id") Long id);

    @PostMapping("/api/stocks/{id}/buy")
    Stock buyStockFromNse(@PathVariable("id") Long id, @RequestParam int quantity);

    @PostMapping("/api/stocks/{id}/sell")
    Stock sellStockToNse(@PathVariable("id") Long id, @RequestParam int quantity);
}
